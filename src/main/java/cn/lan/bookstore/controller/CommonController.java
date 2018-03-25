package cn.lan.bookstore.controller;

import cn.lan.bookstore.common.Const;
import cn.lan.bookstore.convertor.UserInfoVOConvertor;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.enums.LoginStatusEnum;
import cn.lan.bookstore.enums.ResponseCodeEnum;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.IUserBaseInfoService;
import cn.lan.bookstore.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/20/2018 12:31 PM
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private IUserBaseInfoService userBaseInfoService;

    /**
     * 注册
     * @param userInfoVO
     *  userName;
     *  email;
     *  phone;
     *  password;
     * @return
     */
    @PostMapping("/register")
    public BaseResponse register(UserInfoVO userInfoVO) {
        if (!userInfoVO.check()) {
            return new BaseResponse(ResponseCodeEnum.INCOMPLETE_INFO);
        }
        // register
        synchronized (this) {
            // 校验用户名
            if (userBaseInfoService.checkUserName(userInfoVO.getUserName())) {
                return new BaseResponse<>(ResponseCodeEnum.INVALID_USERNAME);
            }
            // 校验手机
            if (userBaseInfoService.checkPhone(userInfoVO.getPhone())) {
                return new BaseResponse(ResponseCodeEnum.INVALID_PHONE);
            }
            userBaseInfoService.register(UserInfoVOConvertor.userInfoVO2UserBaseInfoEntity(userInfoVO));
        }
        return BaseResponse.SUCCESS;
    }
    @PostMapping("/login")
    public BaseResponse<String> login(@RequestParam(value = "userName",required = false)String username,
                                      @RequestParam(value = "phone",required = false) Long phone ,
                                      @RequestParam(value = "password",required = false) String password,
                                      HttpSession httpSession) {
        // has login ?
        if (LoginStatusEnum.LOGIN.getCode().equals(httpSession.getAttribute(Const.LOGIN_STATUS))) {
            return BaseResponse.SUCCESS;
        }
        if (username == null && phone == null) {
            return new BaseResponse<String>(ResponseCodeEnum.INCOMPLETE_INFO);
        }
        // check password
        UserBaseInfoDTO userBaseInfoDTO = new UserBaseInfoDTO();
        userBaseInfoDTO.setUserName(username);
        userBaseInfoDTO.setPhone(phone);
        userBaseInfoDTO.setPassword(password);
        if (userBaseInfoService.check(userBaseInfoDTO)) {
            // 标记登录状态
            httpSession.setAttribute(Const.LOGIN_STATUS, LoginStatusEnum.LOGIN.getCode());
            return BaseResponse.SUCCESS;
        } else return new BaseResponse<String>(ResponseCodeEnum.INCOMPLETE_INFO);
    }
}
