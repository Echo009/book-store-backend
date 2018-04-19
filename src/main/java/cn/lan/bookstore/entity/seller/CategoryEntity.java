package cn.lan.bookstore.entity.seller;

import lombok.Data;

import javax.persistence.*;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:22 PM
 * @author Ech0
 */

@Entity
@Data
@Table(name = "category",uniqueConstraints = @UniqueConstraint(columnNames = {"tag"}))
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String tag ;

}
