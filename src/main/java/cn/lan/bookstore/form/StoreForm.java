package cn.lan.bookstore.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/20/2018 12:34 PM
 */
@Data
public class StoreForm {

    @NotEmpty(message = "店铺名称不能为空 ！")
    private String storeName;

    private String storeCoverImg;

    @NotNull(message = "手机号码无效 ！")
    private Long registerPhone;

    @NotEmpty(message = "银行卡号不能为空 ！")
    private String bankNum;

    @NotEmpty(message = "申请人姓名不能为空 ！")
    private String realName;
    @NotEmpty(message = "身份证号码不能为空 ！")
    private String idNumber;

    private String email;
    @NotEmpty(message = "地址不能为空 ！")
    private String addr;

}
