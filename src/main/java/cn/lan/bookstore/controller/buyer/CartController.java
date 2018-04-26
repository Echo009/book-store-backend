package cn.lan.bookstore.controller.buyer;

import cn.lan.bookstore.controller.BaseController;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.entity.buyer.CartEntity;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.buyer.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        ResultDTO<CartEntity> cartEntityResultDTO = cartService.addCart(userBaseInfoDTO.getUserId(), bookId, amount);
        return new BaseResponse(true, cartEntityResultDTO.getData());

    }

    @PostMapping("/remove")
    public BaseResponse remove(@RequestParam Long cartId) {

        UserBaseInfoDTO userBaseInfoDTO = getCurrentUserInfo();
        cartService.removeCart(userBaseInfoDTO.getUserId(),cartId);
        return BaseResponse.SUCCESS;
    }

    @PostMapping("/removeAll")
    public BaseResponse removeAll() {

        UserBaseInfoDTO userBaseInfoDTO = getCurrentUserInfo();
        cartService.removeAllCart(userBaseInfoDTO.getUserId());
        return BaseResponse.SUCCESS;
    }


    @PostMapping("/all")
    public BaseResponse all() {

        UserBaseInfoDTO userBaseInfoDTO = getCurrentUserInfo();
        List result = cartService.findCartsByUserId(userBaseInfoDTO.getUserId());
        return new BaseResponse(true,result);
    }
}
