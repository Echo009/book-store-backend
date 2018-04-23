package cn.lan.bookstore.aspect;

import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/23/2018 03:19 PM
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution( public * cn.lan.bookstore.controller.*.*.*(..))")
    public void handleRequest() {

    }

    @Before("handleRequest()")
    public void doLogParam() {
        log.info("----------------------------------------------");
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("request uri is : {}", request.getRequestURI());
        Map<String, String[]> map = request.getParameterMap();
        map.entrySet().forEach(e -> {
            log.info("\t{} \t--\t {}\t", e.getKey(), Arrays.toString(e.getValue()));
        });
        log.info("----------------------------------------------");
    }

    @AfterReturning(value = "handleRequest()", returning = "response")
    public void doLogResponse(BaseResponse response) {
        log.info("==============================================");
        log.info("\t code :\t  {}",response.getCode());
        log.info("\t desc :\t  {}",response.getDesc());
        if (response.getData() != null) {
            log.info("\t data :\t  {}",JsonUtil.toJson(response.getData(),false));
        }
        log.info("==============================================");
    }
}
