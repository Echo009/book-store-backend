package cn.lan.bookstore.controller;

import cn.lan.bookstore.constant.CookieConstant;
import cn.lan.bookstore.constant.RedisConstant;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.enums.ResponseCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.util.CookiesUtil;
import cn.lan.bookstore.util.JsonUtil;
import com.qiniu.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/21/2018 03:54 PM
 */
@RestController
@Slf4j
public class BaseController {
    @Autowired
    public StringRedisTemplate redisTemplate;

    public UserBaseInfoDTO getCurrentUserInfo(){
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie cookie = CookiesUtil.get(request, CookieConstant.TOKEN);

        if(cookie==null){
            log.warn("【获取当前用户信息】token cookie不存在");
            throw new BaseServerException(ResponseCodeEnum.NO_LOGIN);
        }
        // 查询token有效性
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("【获取当前用户信息】无效token");
            throw new BaseServerException(ResponseCodeEnum.INVALID_TOKEN);
        }

        return JsonUtil.toBean(tokenValue, UserBaseInfoDTO.class);
    }
}
