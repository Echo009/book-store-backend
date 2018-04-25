package cn.lan.bookstore.form.seller;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/25/2018 08:49 AM
 */
@Data
public class BookDeatilForm {
    /**
     * 更新商品信息的时候需要
     */
    private Long id;
    @Min(value = 1 ,message = "书籍ID不能为空 ！")
    private Long bookId;
    @Min(value = 1 ,message = "请输入合理的版本号 ！")
    private Integer edition;

    private Integer pages = 0;

    private  Integer words = 0;
    /**
     * yyyy-MM-dd
     */
    @NotEmpty(message = "印刷日期不能为空 ！")
    private String printDate;
    @NotEmpty(message = "纸张类型不能为空 ！")
    private String paperType;
    @NotEmpty(message = "简介不能为空 ！")
    private String briefIntro;
    @NotEmpty(message = "作者简介不能为空 ！")
    private String authorIntro;
    /**
     * 前言
     */
    private String foreword ;
    /**
     * 详情图片地址，以;分隔
     */
    @NotEmpty(message = "请上传书籍详情图片 ！")
    private String images;

}
