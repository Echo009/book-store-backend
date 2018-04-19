package cn.lan.bookstore.controller.seller;

import cn.lan.bookstore.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : Ech0
 *
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/19/2018 04:09 PM
 */

@RestController
@Slf4j
@RequestMapping("/store")
public class StoreController {

    @RequestMapping("/test")
    public BaseResponse test(){
        return BaseResponse.SUCCESS;
    }

}
