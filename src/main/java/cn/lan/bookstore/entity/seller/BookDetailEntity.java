package cn.lan.bookstore.entity.seller;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:15 PM
 * @author Ech0
 */

@Entity
@Data
@Table(name ="book_detail",uniqueConstraints = {@UniqueConstraint(columnNames = {"bookId"})})
public class BookDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

    private Integer edition;

    private Integer pages = 0;

    private  Integer words = 0;

    private String printDate;

    private String paperType;

    private String briefIntro;

    private String authorIntro;
    /**
     * 推薦
     */
    private String foreword = "emmmm .... 暫時木有 ! ";
    /**
     * 详情图片地址，以;分隔
     */
    private String images;

}
