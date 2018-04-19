package cn.lan.bookstore.aspect;

import cn.lan.bookstore.constant.CookieConstant;
import cn.lan.bookstore.constant.RedisConstant;
import cn.lan.bookstore.enums.ResponseCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.util.CookiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/19/2018 04:36 PM
 */
@Aspect
@Component
@Slf4j
public class AuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution( public *  cn.lan.bookstore.controller.seller.*.*(..))" +
            "|| execution(public * cn.lan.bookstore.controller.buyer.*.*(..))")
    public void verify(){

    }
    @Before("verify()")
    public void doVerify(){
        log.info("do verifying ...");
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 校验cookie
        Cookie cookie = CookiesUtil.get(request, CookieConstant.TOKEN);

        if(cookie==null){
            log.warn("【登录校验】token cookie不存在");
            throw new BaseServerException(ResponseCodeEnum.NO_LOGIN);
        }
        // 查询token有效性

        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】无效token");
            throw new BaseServerException(ResponseCodeEnum.INVALID_TOKEN);
        }
    }
}
