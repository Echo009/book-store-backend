package cn.lan.bookstore.controller.buyer;

import cn.lan.bookstore.controller.BaseController;
import cn.lan.bookstore.entity.buyer.AddressEntity;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.form.buyer.AddressForm;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.buyer.IAddressService;
import cn.lan.bookstore.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/27/2018 10:23 AM
 */
@Slf4j
@RestController
@RequestMapping("/address")
@CrossOrigin("*")
public class AddressController extends BaseController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping("/add")
    public BaseResponse add(@Valid AddressForm addressForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【添加地址】参数不正确，addressForm={}", JsonUtil.toJson(addressForm, true));
            throw new BaseServerException(
                    ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        AddressEntity addressEntity = new AddressEntity();
        BeanUtils.copyProperties(addressForm, addressEntity);
        addressEntity.setCreatTime(new Date());
        addressEntity = addressService.add(addressEntity, getCurrentUserInfo().getUserId());
        return new BaseResponse(true, addressEntity);
    }

    @RequestMapping("/remove")
    public BaseResponse remove(Long id) {
        addressService.remove(id,getCurrentUserInfo().getUserId());
        return BaseResponse.SUCCESS;
    }

    @RequestMapping("/all")
    public BaseResponse all() {
        List result = addressService.findAll(getCurrentUserInfo().getUserId());
        return new BaseResponse(true, result);
    }
    public BaseResponse update( @Valid AddressForm addressForm, BindingResult bindingResult ) {
        if (bindingResult.hasErrors()) {
            log.error("【修改地址】参数不正确，addressForm={}", JsonUtil.toJson(addressForm, true));
            throw new BaseServerException(
                    ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        if (addressForm.getId() == null) {
            return new BaseResponse(ResponseCodeEnum.ILLEGAL_ARGUMENT);
        }
        AddressEntity addressEntity = new AddressEntity();
        BeanUtils.copyProperties(addressForm,addressEntity);
        addressEntity =addressService.update(addressEntity,getCurrentUserInfo().getUserId());
        return new BaseResponse(true, addressEntity);
    }

}
