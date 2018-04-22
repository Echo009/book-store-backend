package cn.lan.bookstore.controller.seller;

import cn.lan.bookstore.constant.CookieConstant;
import cn.lan.bookstore.constant.RedisConstant;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.entity.common.UserBaseInfoEntity;
import cn.lan.bookstore.entity.seller.StoreEntity;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.enums.common.RoleCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.form.StoreForm;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.common.IUserBaseInfoService;
import cn.lan.bookstore.service.seller.IStoreService;
import cn.lan.bookstore.util.CookiesUtil;
import cn.lan.bookstore.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

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

    @Autowired
    private IStoreService storeService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserBaseInfoService userBaseInfoService;

    @PostMapping("/create")
    public BaseResponse createStore(@Valid StoreForm storeForm,
                                    BindingResult bindingResult,
                                    HttpServletRequest request) {


        if (bindingResult.hasErrors()) {
            log.error("【申请店铺】参数不正确，storeForm={}", JsonUtil.toJson(storeForm, true));
            throw new BaseServerException(
                    ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        String token = CookiesUtil.get(request, CookieConstant.TOKEN).getValue();
        // 校验当前申请人状态
        String userInfoJsonString  = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, token));

        UserBaseInfoDTO userBaseInfoDTO = JsonUtil.toBean(userInfoJsonString, UserBaseInfoDTO.class);
        // 为买家则可以申请
        if (RoleCodeEnum.BUYER.getCode().equals(userBaseInfoDTO.getRoleCode())){

            StoreEntity storeEntity = new StoreEntity();
            BeanUtils.copyProperties(storeForm,storeEntity);

            storeEntity.setUserId(userBaseInfoDTO.getUserId());
            storeEntity.setRegistDate(new Date());
            storeEntity = storeService.createStore(storeEntity);
            // 更新角色信息
            // redis
            userBaseInfoDTO.setRoleCode(RoleCodeEnum.SELLER.getCode());
            redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),JsonUtil.toJson(userBaseInfoDTO,true));
            // db
            log.info("create store , curent user  {}",JsonUtil.toJson(userBaseInfoDTO,true ));
            UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoById(userBaseInfoDTO.getUserId());
            userBaseInfoEntity.setRoleCode(RoleCodeEnum.SELLER.getCode());
            userBaseInfoService.updateUserInfo(userBaseInfoEntity);

            return new BaseResponse(true, storeEntity);
        }
        return new BaseResponse(ResponseCodeEnum.VIOLATION_OPERATION);
    }
    @PostMapping("/update")
    public BaseResponse updateStore(@Valid StoreForm storeForm,
                                    BindingResult bindingResult,
                                    HttpServletRequest request) {


        if (bindingResult.hasErrors()) {
            log.error("【修改店铺】参数不正确，storeForm={}", JsonUtil.toJson(storeForm, true));
            throw new BaseServerException(
                    ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        String token = CookiesUtil.get(request, CookieConstant.TOKEN).getValue();
        // 校验当前申请人状态
        String userInfoJsonString  = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, token));

        UserBaseInfoDTO userBaseInfoDTO = JsonUtil.toBean(userInfoJsonString, UserBaseInfoDTO.class);
        // 为商家且已有店铺则可以申请

        StoreEntity storeEntity =  storeService.findStoreByUserId(userBaseInfoDTO.getUserId());
        if (RoleCodeEnum.SELLER.getCode().equals(userBaseInfoDTO.getRoleCode())&&
               storeEntity!=null){

            BeanUtils.copyProperties(storeForm,storeEntity,
                    "registerDate","userId","id","status");

            storeEntity.setUpdateDate(new Date());
            storeEntity = storeService.createStore(storeEntity);
            return new BaseResponse(true, storeEntity);
        }
        return new BaseResponse(ResponseCodeEnum.VIOLATION_OPERATION);
    }

    /**
     * 检查店铺名是否可用
     * @param storeName
     * @return
     */
    @PostMapping("/checkName")
    public BaseResponse checkName(String storeName) {
        if (storeService.findStoreByName(storeName) != null) {
            return new BaseResponse(ResponseCodeEnum.INVALID_STORE_NAME);
        }else {
            return BaseResponse.SUCCESS;
        }
    }

}
