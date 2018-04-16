package cn.lan.bookstore.entity.seller;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:15 PM
 */

@Entity(name = "book_detail")
@Data
public class BookDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

    private Integer edition;

    private Integer pages;

    private  Integer words;

    private Date printDate;

    private String paperType;

    private String briefIntro;

    private String authorIntro;

    private String foreword ;
    /**
     * 详情图片地址，以;分隔
     */
    private String images;

}
