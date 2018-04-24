package cn.lan.bookstore.form.seller;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/21/2018 03:49 PM
 */
@Data
public class BookSimpleForm {

    private Long id;
    @NotEmpty(message = "书籍名称不能为空 ！")
    private String bookName;
    @NotNull(message = "推荐语不能为空 ！")
    private String recommendation;
    @NotEmpty(message = "作者名称不能为空 ！")
    private String authorName;
    @NotEmpty(message = "出版社信息不能为空 ！")
    private String publisher ;
    @NotEmpty(message = "出版日期不能为空 ！")
    private String publishDate;
    @DecimalMin(value = "0.00",message = "定价不能低于0 ！")
    private BigDecimal pricing;

    private BigDecimal discount = new BigDecimal("0");
    @DecimalMin(value = "0.00",message = "售价不能低于0 ！")
    private BigDecimal currentPrice;
    @Min(value = 0, message = " 库存必须大于等于0 !")
    private Integer stock;
    /**
     * 封面图片地址，多个图片之间以 ; 分隔
     */
    @NotEmpty(message = "封面图不能为空 ！")
    private String coverImg;
    /**
     * 分类，多个分类之间以 ; 分隔
     */
    @NotEmpty(message = "分类不能为空 ！")
    private String category;

}
