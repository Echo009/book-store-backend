package cn.lan.bookstore.controller.buyer;

import cn.lan.bookstore.controller.BaseController;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.buyer.IOrderService;
import cn.lan.bookstore.vo.OrderWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 05/23/2018 10:26 PM
 */

@RestController
@CrossOrigin("*")
@Slf4j
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;
    @PostMapping("/generate")
    BaseResponse generateOrder(List<Long> cartIdList,Long addressId) {
       OrderWrapper orderWrapper = orderService.generateOrder(cartIdList, addressId,getCurrentUserInfo().getUserId());
        return new BaseResponse(true, orderWrapper);
    }

    @RequestMapping("/cancel")
    BaseResponse cancelOrder(String orderMasterId){
        orderService.cancelOrder(orderMasterId,getCurrentUserInfo().getUserId());
        return BaseResponse.SUCCESS;
    }

    @RequestMapping("/pay")
    BaseResponse payOrder(String orderMasterId){
        orderService.payOrder(orderMasterId,getCurrentUserInfo().getUserId());
        return BaseResponse.SUCCESS;
    }

}
