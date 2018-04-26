package cn.lan.bookstore.controller.common;

import cn.lan.bookstore.controller.BaseController;
import cn.lan.bookstore.convertor.BookInfoDeatilVOConvertor;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.entity.seller.BookDetailEntity;
import cn.lan.bookstore.entity.seller.BookEntity;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.seller.IBookDetailService;
import cn.lan.bookstore.service.seller.IBookService;
import cn.lan.bookstore.vo.BookInfoDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/25/2018 04:48 PM
 */

@RequestMapping("/bookInfo")
@RestController
@Slf4j
@CrossOrigin("*")
public class BookInfoController extends BaseController {
    @Autowired
    private IBookService bookService;
    @Autowired
    private IBookDetailService bookDetailService;


    @RequestMapping("/search")
    public BaseResponse searchBook(@RequestParam String bookName,
                                   @RequestParam Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<BookEntity> result = bookService.findBooksByBookName(bookName, pageSize, pageNum);
        return new BaseResponse(true, result);
    }

    @RequestMapping("/detail")
    public BaseResponse detail(@RequestParam Long bookId) {
        if (bookId == null) {
            return new BaseResponse(ResponseCodeEnum.ILLEGAL_ARGUMENT);
        }
        BookEntity bookEntity = bookService.findBookById(bookId);
        if (bookEntity == null) {
            return BaseResponse.ERROR;
        }
        ResultDTO<BookDetailEntity> resultDTO = bookDetailService.findBookDeatailByBookId(bookId);
        BookDetailEntity bookDetailEntity = resultDTO.getData();
        BookInfoDetailVO bookInfoDetailVO = BookInfoDeatilVOConvertor.convertoBookInfoDetailVo(bookEntity, bookDetailEntity);
        return new BaseResponse(true, bookInfoDetailVO);
    }
}
