package cn.lan.bookstore.entity.buyer;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:43 PM
 * @author Ech0
 */

@Entity
@Data
@Table(name = "favorite",uniqueConstraints = {@UniqueConstraint(name = "u_1",columnNames = {"userId","contentId"})})
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Short type;

    private String coverImg;

    private Long contentId;

    private String name ;

    private Date createTime;
}
