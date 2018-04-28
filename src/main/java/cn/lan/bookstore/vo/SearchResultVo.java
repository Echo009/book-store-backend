package cn.lan.bookstore.vo;

import cn.lan.bookstore.entity.seller.BookEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/28/2018 01:23 AM
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SearchResultVo {

    private Integer currentPage;

    private Long totalElements;

    private Integer totalPages;

    private List<BookEntity> content;

}
