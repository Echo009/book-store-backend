package cn.lan.bookstore.controller.seller;

import cn.lan.bookstore.controller.BaseController;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.entity.seller.BookEntity;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.form.seller.BookSimpleForm;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.seller.IBookService;
import cn.lan.bookstore.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/21/2018 03:46 PM
 */
@RestController
@RequestMapping("/book")
@Slf4j
public class BookController extends BaseController {
    @Autowired
    private IBookService bookService;


    @PostMapping("/add")
    public BaseResponse addBook(@Valid BookSimpleForm bookSimpleForm,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【添加书籍】参数不正确，bookSimpleForm={}", JsonUtil.toJson(bookSimpleForm, true));
            throw new BaseServerException(
                    ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserBaseInfoDTO curentUser = getCurrentUserInfo();
        BookEntity bookEntity = new BookEntity();
        BeanUtils.copyProperties(bookSimpleForm, bookEntity);
        ResultDTO<BookEntity> resultDTO = bookService.addBook(curentUser.getUserId(), bookEntity);
        if (resultDTO.isSuccess()) {
            return new BaseResponse(true, resultDTO.getData());
        } else {
            if (resultDTO.getData() != null) {
                return new BaseResponse(false, resultDTO.getData());
            } else {
                return new BaseResponse(ResponseCodeEnum.VIOLATION_OPERATION);
            }
        }
    }

    @RequestMapping("/delete")
    public BaseResponse deleteBook(@RequestParam Long bookId) {
        UserBaseInfoDTO currentUser = getCurrentUserInfo();
        ResultDTO resultDTO = bookService.deleteBook(currentUser.getUserId(), bookId);
        if (resultDTO.isSuccess()) {
            return BaseResponse.SUCCESS;
        } else {
            return new BaseResponse(false, resultDTO.getData());
        }
    }

    @PostMapping("/update")
    public BaseResponse updateBook(@Valid BookSimpleForm bookSimpleForm,
                                   BindingResult bindingResult) {

        if (bookSimpleForm.getId() == null) {
            log.error("【修改书籍信息】书籍id不能为空，bookSimpleForm={}", JsonUtil.toJson(bookSimpleForm, true));
            throw new BaseServerException(
                    ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(),
                    "书籍id不能为空");
        }
        if (bindingResult.hasErrors()) {
            log.error("【修改书籍信息】参数不正确，bookSimpleForm={}", JsonUtil.toJson(bookSimpleForm, true));
            throw new BaseServerException(
                    ResponseCodeEnum.ILLEGAL_ARGUMENT.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserBaseInfoDTO curentUser = getCurrentUserInfo();
        BookEntity bookEntity = new BookEntity();
        BeanUtils.copyProperties(bookSimpleForm, bookEntity);
        ResultDTO<BookEntity> resultDTO = bookService.updateBookInfo(curentUser.getUserId(), bookEntity);
        if (resultDTO.isSuccess()) {
            return new BaseResponse(true, resultDTO.getData());
        } else {
            return new BaseResponse(ResponseCodeEnum.VIOLATION_OPERATION);
        }
    }

    @PostMapping("/search")
    public BaseResponse searchBook(@RequestParam String bookName,
                                   @RequestParam Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<BookEntity> result = bookService.findBooksByBookName(bookName, pageSize, pageNum);
        return new BaseResponse(true, result);

    }

    @RequestMapping("/all")
    public BaseResponse findAllBooks() {
       List results = bookService.findAllBooksByUserId(getCurrentUserInfo().getUserId());
       return new BaseResponse(true,results);
    }
}
