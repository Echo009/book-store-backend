package cn.lan.bookstore.entity.buyer;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:43 PM
 */

@Entity(name = "favorite")
@Data
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Long userId;

    private Short type;

    private Long contentId;
}
