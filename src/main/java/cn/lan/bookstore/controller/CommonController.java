package cn.lan.bookstore.controller;

import cn.lan.bookstore.constant.CookieConstant;
import cn.lan.bookstore.constant.RedisConstant;
import cn.lan.bookstore.convertor.UserInfoVOConvertor;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.entity.common.UserBaseInfoEntity;
import cn.lan.bookstore.enums.ResponseCodeEnum;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.IUserBaseInfoService;
import cn.lan.bookstore.util.CookiesUtil;
import cn.lan.bookstore.util.JsonUtil;
import cn.lan.bookstore.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/20/2018 12:31 PM
 *
 * @author Ech0
 */
@RestController
@RequestMapping("/common")
@CrossOrigin(origins = "*")
public class CommonController {

    @Autowired
    private IUserBaseInfoService userBaseInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 注册
     *
     * @param userInfoVO userName;
     *                   email;
     *                   phone;
     *                   password;
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
    public BaseResponse<UserBaseInfoDTO> login(@RequestParam(value = "userName", required = false) String username,
                                               @RequestParam(value = "phone", required = false) Long phone,
                                               @RequestParam(value = "password", required = false) String password,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        //  校验登录状态
        Cookie cookie = CookiesUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            String value = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            if (!StringUtils.isEmpty(value)) {
                // 已经登录
                return new BaseResponse(ResponseCodeEnum.REPEAT_LOGIN);
            }
        }

        if (username == null && phone == null) {
            return new BaseResponse(ResponseCodeEnum.INCOMPLETE_INFO);
        }
        // check password
        UserBaseInfoDTO userBaseInfoDTO = new UserBaseInfoDTO();
        userBaseInfoDTO.setUserName(username);
        userBaseInfoDTO.setPhone(phone);
        userBaseInfoDTO.setPassword(password);
        ResultDTO<UserBaseInfoEntity> resultDTO = userBaseInfoService.check(userBaseInfoDTO);
        if (resultDTO.isSuccess()) {
            // 标记登录状态
            // 将token 值写入 redis
            String token = UUID.randomUUID().toString();
            Integer expire = RedisConstant.EXPIRE;

            userBaseInfoDTO.setPassword(null);
            userBaseInfoDTO.setRole_code(resultDTO.getData().getRoleCode());
            userBaseInfoDTO.setUserName(resultDTO.getData().getUserName());
            // 将token 以及用户基本信息 写入redis
            redisTemplate.opsForValue().set(
                    String.format(RedisConstant.TOKEN_PREFIX, token),
                    JsonUtil.toJson(userBaseInfoDTO, false),
                    expire, TimeUnit.SECONDS);

            // 写入cookie ， 注意与redis中的值不同在于没有前缀
            CookiesUtil.set(response, CookieConstant.TOKEN, token, expire);

            return new BaseResponse<UserBaseInfoDTO>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDesc(), userBaseInfoDTO);

        } else {
            return new BaseResponse(ResponseCodeEnum.INCORRECT_INFO);
        }
    }

    @RequestMapping("/logout")
    public BaseResponse logout(HttpServletRequest request,
                               HttpServletResponse response) {

        Cookie cookie = CookiesUtil.get(request, CookieConstant.TOKEN);
        if (cookie!=null) {
            // 清除 redis中的数据
            redisTemplate.opsForValue().getOperations()
                    .delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            // 清除 cookie
            CookiesUtil.set(response, CookieConstant.TOKEN, null, 0);
            return new BaseResponse(ResponseCodeEnum.SUCCESS);
        } else {
            return new BaseResponse(ResponseCodeEnum.ERROR);
        }
    }
}
