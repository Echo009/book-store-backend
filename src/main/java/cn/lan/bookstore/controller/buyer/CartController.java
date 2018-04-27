package cn.lan.bookstore.controller.buyer;

import cn.lan.bookstore.controller.BaseController;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.entity.buyer.CartEntity;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.buyer.ICartService;
import cn.lan.bookstore.vo.CartWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/26/2018 10:18 AM
 */
@Slf4j
@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController extends BaseController {
    @Autowired
    private ICartService cartService;

    @PostMapping("/add")
    public BaseResponse add(@RequestParam Long bookId, @RequestParam(required = false) Integer amount) {
        UserBaseInfoDTO userBaseInfoDTO = getCurrentUserInfo();
        // 如果添加失败则说明是重复添加。

        ResultDTO cartEntityResultDTO = cartService.addCart(userBaseInfoDTO.getUserId(), bookId, amount);


        return new BaseResponse(true, cartEntityResultDTO.getData());
    }

    @PostMapping("/remove")
    public BaseResponse remove(@RequestParam Long cartId) {

        UserBaseInfoDTO userBaseInfoDTO = getCurrentUserInfo();
        cartService.removeCart(userBaseInfoDTO.getUserId(), cartId);
        return BaseResponse.SUCCESS;
    }

    @PostMapping("/removeAll")
    public BaseResponse removeAll() {

        UserBaseInfoDTO userBaseInfoDTO = getCurrentUserInfo();
        cartService.removeAllCart(userBaseInfoDTO.getUserId());
        return BaseResponse.SUCCESS;
    }


    @RequestMapping("/all")
    public BaseResponse all() {

        UserBaseInfoDTO userBaseInfoDTO = getCurrentUserInfo();
        List<CartEntity> result = cartService.findCartsByUserId(userBaseInfoDTO.getUserId());
        // 按店铺分类处理
        final List<CartWrapper> carts = new LinkedList<>();
        Long perStoreId = null;
        CartWrapper wrapper = null;
        for (CartEntity cartEntity : result) {
            if (perStoreId == null || !cartEntity.getStoreId().equals(perStoreId)) {
                // new
                if (wrapper != null) {
                    carts.add(wrapper);
                }
                wrapper = new CartWrapper();
                wrapper.setStoreId(cartEntity.getStoreId());
                wrapper.setStoreName(cartEntity.getStoreName());
                wrapper.setCartEntityList(new LinkedList<>());
                perStoreId = cartEntity.getStoreId();
            }
            wrapper.getCartEntityList().add(cartEntity);
        }
        if (wrapper == null) {
            return new BaseResponse(true, null);
        }
        carts.add(wrapper);
        return new BaseResponse(true, carts);
    }
}
