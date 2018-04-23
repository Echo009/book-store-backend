package cn.lan.bookstore.controller.common;

import cn.lan.bookstore.constant.CookieConstant;
import cn.lan.bookstore.constant.RedisConstant;
import cn.lan.bookstore.convertor.UserInfoVOConvertor;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.entity.common.UserBaseInfoEntity;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.common.IUserBaseInfoService;
import cn.lan.bookstore.util.CookiesUtil;
import cn.lan.bookstore.util.JsonUtil;
import cn.lan.bookstore.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
                log.warn("【登录】重复登录 current user：{}",value);
                return new BaseResponse(ResponseCodeEnum.REPEAT_LOGIN);
            }
        }

        if (username == null && phone == null) {
            log.warn("【登录】用户信息不完整 ");
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
            userBaseInfoDTO.setUserId(resultDTO.getData().getId());
            userBaseInfoDTO.setRoleCode(resultDTO.getData().getRoleCode());
            userBaseInfoDTO.setUserName(resultDTO.getData().getUserName());
            userBaseInfoDTO.setHeadImg(resultDTO.getData().getHeadImg());
            // 将token 以及用户基本信息 写入redis
            String currentUserInfo = JsonUtil.toJson(userBaseInfoDTO, false);
            redisTemplate.opsForValue().set(
                    String.format(RedisConstant.TOKEN_PREFIX, token),
                    currentUserInfo,
                    expire, TimeUnit.SECONDS);
            log.info("【登录】write redis , token : {} current user :{}",token,currentUserInfo);

            // 写入cookie ， 注意与redis中的值不同在于没有前缀
            CookiesUtil.set(response, CookieConstant.TOKEN, token, expire);

            return new BaseResponse<UserBaseInfoDTO>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDesc(), userBaseInfoDTO);

        } else {
            log.warn("【登录】用户信息错误 current user :{}",username);
            return new BaseResponse(ResponseCodeEnum.INCORRECT_INFO);
        }
    }

    @RequestMapping("/logout")
    public BaseResponse logout(HttpServletRequest request,
                               HttpServletResponse response) {

        Cookie cookie = CookiesUtil.get(request, CookieConstant.TOKEN);
        for (Cookie item : request.getCookies()) {
            log.info("【注销】 cookie of request : {} - {}" , item.getName(),item.getValue() );
        }
        if (cookie!=null) {
            String userString = redisTemplate.opsForValue()
                    .get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            if (userString == null) {
                log.warn("【注销】无效token : {}",cookie.getValue());
                // 清除 cookie
                CookiesUtil.set(response, CookieConstant.TOKEN, null, 0);
                return new BaseResponse(ResponseCodeEnum.ERROR);
            }
            log.info("【注销登录】current user : {}",userString);
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

    @RequestMapping("/userInfo")
    public BaseResponse currentUserInfo(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie = CookiesUtil.get(request, CookieConstant.TOKEN);
        for (Cookie item : request.getCookies()) {
            log.info("【获取当前用户信息】 cookie of request : {} - {}" , item.getName(),item.getValue() );
        }
        if (cookie!=null) {
            String userString = redisTemplate.opsForValue()
                    .get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            if (userString == null) {
                log.warn("【获取当前用户信息】无效token : {}",cookie.getValue());
                // 清除 cookie
                CookiesUtil.set(response, CookieConstant.TOKEN, null, 0);
                return new BaseResponse(ResponseCodeEnum.ERROR);
            }
            log.info("【获取当前用户信息】current user : {}",userString);
            UserBaseInfoDTO userBaseInfoDTO = JsonUtil.toBean(userString,UserBaseInfoDTO.class);
            return new BaseResponse(true,userBaseInfoDTO);
        } else {
            return new BaseResponse(ResponseCodeEnum.ERROR);
        }

    }
}
