package cn.lan.bookstore.form.buyer;

import cn.lan.bookstore.constant.Constant;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/27/2018 10:26 AM
 */
@Data
public class AddressForm {
    private Long id;
    @NotEmpty(message = "收件人姓名不能为空 ！")
    private String name;
    @NotNull(message = "收件人手机号码不能为空 ！")
    private Long phone;
    private String country = Constant.DEFAULT_COUNTRY;
    @NotEmpty(message = "省份不能为空 ！")
    private String province;
    @NotEmpty(message = "城市不能为空 ！")
    private String city;
    @NotEmpty(message = "地区不能为空 ！")
    private String area;
    @NotEmpty(message = "详细地址不能为空 ！")
    private String detail;
}
