package cn.lan.bookstore.controller.common;

import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.common.IUserBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 03:35 PM
 * @author Ech0
 */
@RestController
@RequestMapping("/userInfo")
@CrossOrigin(origins = "*")
public class UserInfoController {

    @Autowired
    private IUserBaseInfoService IUserBaseInfoService;

    @RequestMapping("/checkUserName")
    public BaseResponse checkUserName(@RequestParam(required = true) String userName){
        // 用戶名已經存在
        if (IUserBaseInfoService.checkUserName(userName)) {
            return BaseResponse.SUCCESS;
        }else {
            return BaseResponse.ERROR;
        }
    }

    @RequestMapping("/checkUserPhone")
    public BaseResponse checkUserPhone(@RequestParam(required = true) Long phone){
        // 手机号码存在
        if (IUserBaseInfoService.checkPhone(phone)) {
            return BaseResponse.SUCCESS;
        }else {
            return BaseResponse.ERROR;
        }
    }


}
