package cn.lan.bookstore.convertor;

import cn.lan.bookstore.entity.seller.BookDetailEntity;
import cn.lan.bookstore.entity.seller.BookEntity;
import cn.lan.bookstore.vo.BookInfoDetailVO;
import org.springframework.beans.BeanUtils;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/25/2018 05:11 PM
 */
public class BookInfoDeatilVOConvertor {

    public static BookInfoDetailVO convertoBookInfoDetailVo(BookEntity bookEntity, BookDetailEntity bookDetailEntity) {
        BookInfoDetailVO bookInfoDetailVO = new BookInfoDetailVO();
        BeanUtils.copyProperties(bookEntity, bookInfoDetailVO);
        bookInfoDetailVO.setBookId(bookEntity.getId());
        if (bookDetailEntity != null) {
            bookInfoDetailVO.setBookDetailId(bookDetailEntity.getId());
            bookInfoDetailVO.setEdition(bookDetailEntity.getEdition());
            bookInfoDetailVO.setPages(bookDetailEntity.getPages());
            bookInfoDetailVO.setWords(bookDetailEntity.getWords());
            bookInfoDetailVO.setPrintDate(bookDetailEntity.getPrintDate());
            bookInfoDetailVO.setPaperType(bookDetailEntity.getPaperType());
            bookInfoDetailVO.setBriefIntro(bookDetailEntity.getBriefIntro());
            bookInfoDetailVO.setAuthorIntro(bookDetailEntity.getAuthorIntro());
            bookInfoDetailVO.setForeword(bookDetailEntity.getForeword());
            bookInfoDetailVO.setImages(bookDetailEntity.getImages());
        }
        return bookInfoDetailVO;
    }


}
