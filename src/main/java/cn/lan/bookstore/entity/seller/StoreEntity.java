package cn.lan.bookstore.entity.seller;

import cn.lan.bookstore.enums.seller.StoreStatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:05 PM
 * @author Ech0
 */
@Data
@Entity
@Table(name = "store",uniqueConstraints = {@UniqueConstraint(columnNames = {"userId"}) ,@UniqueConstraint(columnNames = {"storeName"})} )
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Long userId ;

    private String storeName;

    private String storeCoverImg;

    private Date registDate;

    private Date updateDate;

    private Long registerPhone;

    private String bankNum ;

    private Integer status = StoreStatusEnum.VALID.getCode();

}
