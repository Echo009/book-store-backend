package cn.lan.bookstore.service.seller.impl;

import cn.lan.bookstore.dao.seller.BookDao;
import cn.lan.bookstore.dao.seller.StoreDao;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.entity.seller.BookEntity;
import cn.lan.bookstore.entity.seller.StoreEntity;
import cn.lan.bookstore.enums.seller.ProductStatusEnum;
import cn.lan.bookstore.service.seller.IBookService;
import cn.lan.bookstore.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/21/2018 03:20 PM
 */
@Service
@Slf4j
public class BookServiceImpl implements IBookService {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private StoreDao storeDao;

    /**
     * 新增书籍
     *
     * @param userId
     * @param bookEntity
     * @return
     */
    @Override
    public ResultDTO<BookEntity> addBook(Long userId, BookEntity bookEntity) {
        StoreEntity storeEntity = storeDao.findByUserId(userId);

        //  先检查同一商铺中是否存在同名书籍

        if (storeDao.findByStoreName(bookEntity.getBookName()) != null) {
            log.warn("【新增书籍】 当前店铺已存在该书籍 book ：{}", JsonUtil.toJson(bookEntity, true));
            return new ResultDTO(false, "当前书籍已存在，请不要重复添加！");
        }
        // 校验权限
        if (storeEntity != null) {
            bookEntity.setStoreId(storeEntity.getId());
            bookEntity = bookDao.saveAndFlush(bookEntity);
            log.info("【新增书籍】 userId：{}，book ：{}", userId, JsonUtil.toJson(bookEntity, true));
            return new ResultDTO<>(true, bookEntity);
        } else {
            log.warn("【新增书籍】 permission deny ！");
            return new ResultDTO<>(false, null);
        }
    }

    /**
     * 修改书籍信息
     *
     * @param userId
     * @param bookEntity 信息需完整
     * @return
     */
    @Override
    public ResultDTO<BookEntity> updateBookInfo(Long userId, BookEntity bookEntity) {
        StoreEntity storeEntity = storeDao.findByUserId(userId);
        BookEntity primaryInfo = bookDao.findOne(bookEntity.getId());
        // 校验权限,是否属于该用户店铺下的商品
        if (storeEntity != null && primaryInfo != null &&
                storeEntity.getId().equals(primaryInfo.getStoreId())) {
            bookEntity.setStoreId(storeEntity.getId());
            bookEntity = bookDao.saveAndFlush(bookEntity);
            log.info("【修改书籍信息】 userId：{}，book ：{}", userId, JsonUtil.toJson(bookEntity, true));
            return new ResultDTO<>(true, bookEntity);
        } else {
            log.warn("【修改书籍信息】 permission deny ！");
            return new ResultDTO<>(false, null);
        }
    }

    /**
     * 根据书籍名称分页查询书籍信息
     *
     * @param bookName
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public List<BookEntity> findBooksByBookName(String bookName, Integer pageSize, Integer pageNum) {
        // 构造查询条件
        log.info("【书籍搜索】 book name ：{}", bookName);
        Specification<BookEntity> specification =
                (root, query, cb) -> {
                    List<Predicate> predicates = new LinkedList<>();
                    // 书籍名称模糊查询条件
                    Path<String> $bookName = root.get("name");
                    Predicate _bookName = cb.like($bookName, "%" + bookName + "%");
                    predicates.add(_bookName);

                    // 商品状态
                    Path<Integer> $bookStatus = root.get("status");
                    Predicate _bookStatus = cb.equal($bookStatus, ProductStatusEnum.ON_SALE.getCode());
                    predicates.add(_bookStatus);
                    return cb.and(predicates.toArray(new Predicate[]{}));
                };

        // 按销量排序
        Sort sort = new Sort(Sort.Direction.ASC, "sales");
        Pageable pageable = new PageRequest(pageNum - 1, pageNum, sort);
        List<BookEntity> results = bookDao.findAll(specification, pageable).getContent();
        log.info("【书籍搜索】 results  ：{}", results);
        return results;
    }

    /**
     * 商品下架，逻辑删除
     * @param userId
     * @param bookId
     * @return
     */
    @Override
    public ResultDTO deleteBook(Long userId, Long bookId) {
        BookEntity bookEntity = bookDao.getOne(bookId);
        if (bookEntity == null) {
            log.warn("【删除书籍】current bookId {} is invalid! ");
            return new ResultDTO(false, "bookId 不存在 ！");
        }
        StoreEntity storeEntity = storeDao.findByUserId(userId);
        if (storeEntity != null && storeEntity.getId().equals(bookEntity.getStoreId())) {
            bookEntity.setStatus(ProductStatusEnum.SOLD_OUT.getCode());
        }
        return new ResultDTO(false, "没有权限 ！");
    }

    /**
     * 查询该商户下的所有书籍
     * @param userId
     * @return  my be null
     */
    @Override
    public List<BookEntity> findAllBooksByUserId(Long userId) {
        StoreEntity storeEntity = storeDao.findByUserId(userId);
        if (storeEntity == null) {
            return null;
        }
       return bookDao.findAllByStoreId(storeEntity.getId());
    }
}
