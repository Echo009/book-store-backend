package cn.lan.bookstore.service.buyer.impl;

import cn.lan.bookstore.constant.RedisConstant;
import cn.lan.bookstore.dao.OrderDetailDao;
import cn.lan.bookstore.dao.OrderMasterDao;
import cn.lan.bookstore.dao.buyer.CartDao;
import cn.lan.bookstore.dao.seller.BookDao;
import cn.lan.bookstore.dao.seller.StoreDao;
import cn.lan.bookstore.entity.buyer.CartEntity;
import cn.lan.bookstore.entity.common.OrderDetailEntity;
import cn.lan.bookstore.entity.common.OrderMasterEntity;
import cn.lan.bookstore.entity.seller.BookEntity;
import cn.lan.bookstore.enums.common.OrderStatusEnum;
import cn.lan.bookstore.enums.common.PayStatusEnum;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.service.buyer.IOrderService;
import cn.lan.bookstore.service.seller.IBookService;
import cn.lan.bookstore.util.KeyUtil;
import cn.lan.bookstore.util.RedisLock;
import cn.lan.bookstore.vo.OrderDetailVo;
import cn.lan.bookstore.vo.OrderWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 05/22/2018 07:46 PM
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IBookService bookService;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private RedisLock redisLock;

    /**
     * @param cartIdList 商品需要来源于同一个店铺
     * @param addressId
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderWrapper generateOrder(List<Long> cartIdList, Long addressId, Long userId) {
        if (cartIdList.size() == 0) {
            throw new BaseServerException(ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(), ResponseCodeEnum.ILLEGAL_ARGUMENT.getDesc());
        }
        OrderWrapper orderWrapper = new OrderWrapper();
        List<OrderDetailVo> orderDetailVoList = new ArrayList<>(cartIdList.size());
        CartEntity cartEntity_ = cartDao.findOne(cartIdList.get(0));

        Long sellerId = storeDao.getOne(cartEntity_.getStoreId()).getUserId();
        Long storeId = cartEntity_.getStoreId();

        // 生成订单主表id
        String orderMasterId = KeyUtil.genUniqueKey();
        BigDecimal totalPrice = new BigDecimal("0");
        List<CartEntity> cartEntities = new ArrayList<>(cartIdList.size());
        // 校验购物车是否合法
        for (Long aLong : cartIdList) {
            CartEntity cartEntity = cartDao.findOne(aLong);
            if (cartEntity == null) {
                log.error("【生成订单】指定购物车不存在，cart id :{}", aLong);
                throw new BaseServerException(ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(), "指定购物车不存在，cart id :" + aLong);
            }
            cartEntities.add(cartEntity);
        }
        for (CartEntity cartEntity : cartEntities) {
            if (!cartEntity.getUserId().equals(userId)) {
                throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE.getCode(), ResponseCodeEnum.NO_PRIVILEGE.getDesc());
            }
        }
        // 减库存
        for (CartEntity cartEntity : cartEntities) {
            Long bookId = cartEntity.getBookId();
            BookEntity bookEntity = bookDao.findOne(bookId);
            if (!bookEntity.getStoreId().equals(sellerId)) {
                throw new BaseServerException(ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(), "商品需来自同一个店铺！");
            }
            String lockKey = "Book_" + bookId;
            // 加锁
            if (redisLock.lock(lockKey, RedisConstant.MAX_LOCK_DURATION + "")) {
                try {
                    Integer stock = bookEntity.getStock();
                    stock -= cartEntity.getAmount();
                    if (stock < 0) {
                        throw new BaseServerException(ResponseCodeEnum.ERROR.getCode(), "库存不足！");
                    }
                    bookEntity.setStock(stock);
                    bookDao.saveAndFlush(bookEntity);
                } finally {
                    // 解锁
                    redisLock.unlock(lockKey, RedisConstant.MAX_LOCK_DURATION + "");
                }
            } else {
                throw new BaseServerException(ResponseCodeEnum.ERROR.getCode(), "emmmm,小的忙不过来了，请稍候重试！");
            }
            // 生成订单详情
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            orderDetailEntity.setOrderMasterId(orderMasterId);
            orderDetailEntity.setOrderDeatilId(KeyUtil.genUniqueKey());
            orderDetailEntity.setBookId(bookId);
            orderDetailEntity.setBookName(bookEntity.getBookName());
            orderDetailEntity.setCoverImg(bookEntity.getCoverImg());
            orderDetailEntity.setQuantity(cartEntity.getAmount());
            orderDetailEntity.setPrice(cartEntity.getTotalPrice());
            orderDetailEntity.setStoreId(storeId);
            orderDetailEntity.setSellerId(sellerId);
            orderDetailEntity.setBuyerId(userId);
            orderDetailDao.save(orderDetailEntity);
            OrderDetailVo orderDetailVo = new OrderDetailVo();
            BeanUtils.copyProperties(orderDetailEntity, orderDetailVo);
            orderDetailVoList.add(orderDetailVo);
            totalPrice.add(orderDetailEntity.getPrice());
        }
        // 生成主订单记录
        OrderMasterEntity orderMasterEntity = new OrderMasterEntity();
        orderMasterEntity.setOrderMasterId(orderMasterId);
        orderMasterEntity.setAddrId(addressId);
        orderMasterEntity.setBuyerId(userId);
        orderMasterEntity.setCreateTime(new Date());
        orderMasterEntity.setSellerId(sellerId);
        orderMasterEntity.setStoreId(storeId);
        orderMasterEntity.setOrderAmount(totalPrice);
        orderMasterDao.save(orderMasterEntity);
        BeanUtils.copyProperties(orderMasterEntity, orderWrapper);
        orderWrapper.setOrderDetails(orderDetailVoList);
        return orderWrapper;
    }

    /**
     * 取消订单
     *
     * @param orderMasterId
     * @param userId
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cancelOrder(String orderMasterId, Long userId) {
        OrderMasterEntity orderMasterEntity = orderMasterDao.findByOrderMasterId(orderMasterId);
        if (orderMasterEntity == null) {
            throw new BaseServerException(ResponseCodeEnum.VIOLATION_OPERATION);
        }
        if ((!orderMasterEntity.getBuyerId().equals(userId)) && (!orderMasterEntity.getSellerId().equals(userId))) {
            throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE);
        }
        if (orderMasterEntity.getOrderStatus().equals(OrderStatusEnum.CANCEL)) {
            throw new BaseServerException(ResponseCodeEnum.ERROR.getCode(), "该订单已取消 !");
        }
        List<OrderDetailEntity> orderDetailEntities = orderDetailDao.findAllByOrderMasterId(orderMasterId);
        String lockKey = "OrderMaster_" + orderMasterId;
        if (redisLock.lock(lockKey, RedisConstant.MAX_LOCK_DURATION + "")) {
            try {
                orderMasterEntity.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
                orderMasterDao.save(orderMasterEntity);
                // 还原库存
                for (OrderDetailEntity orderDetailEntity : orderDetailEntities) {
                    bookService.updateStock(orderDetailEntity.getBookId(),orderDetailEntity.getQuantity());
                }
            } finally {
                redisLock.unlock(lockKey, RedisConstant.MAX_LOCK_DURATION + "");
            }
        } else {
            throw new BaseServerException(ResponseCodeEnum.ERROR.getCode(), "系统繁忙，请重试！");
        }
    }

    /**
     * 支付订单
     *
     * @param orderMasterId
     * @param userId
     */
    @Override
    public void payOrder(String orderMasterId, Long userId) {
        OrderMasterEntity orderMasterEntity = orderMasterDao.findByOrderMasterId(orderMasterId);
        if (orderMasterEntity == null) {
            throw new BaseServerException(ResponseCodeEnum.VIOLATION_OPERATION);
        }
        if ((!orderMasterEntity.getBuyerId().equals(userId)) && (!orderMasterEntity.getSellerId().equals(userId))) {
            throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE);
        }
        if (orderMasterEntity.getPayStatus().equals(PayStatusEnum.SUCCESS)) {
            throw new BaseServerException(ResponseCodeEnum.ERROR.getCode(), "该订单已支付 !");
        }
        List<OrderDetailEntity> orderDetailEntities = orderDetailDao.findAllByOrderMasterId(orderMasterId);
        String lockKey = "OrderMaster_" + orderMasterId;
        if (redisLock.lock(lockKey, RedisConstant.MAX_LOCK_DURATION + "")) {
            try {
                // todo
                orderMasterEntity.setPayStatus(PayStatusEnum.SUCCESS.getCode());
                orderMasterDao.save(orderMasterEntity);
                // 更新书籍销量
                for (OrderDetailEntity orderDetailEntity : orderDetailEntities) {
                    bookService.updateSales(orderDetailEntity.getBookId(),orderDetailEntity.getQuantity());
                }
            } finally {
                redisLock.unlock(lockKey, RedisConstant.MAX_LOCK_DURATION + "");
            }
        } else {
            throw new BaseServerException(ResponseCodeEnum.ERROR.getCode(), "系统繁忙，请重试！");
        }

    }
}
