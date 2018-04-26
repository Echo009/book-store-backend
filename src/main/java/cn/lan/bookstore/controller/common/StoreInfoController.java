package cn.lan.bookstore.controller.common;

import cn.lan.bookstore.controller.BaseController;
import cn.lan.bookstore.entity.seller.BookEntity;
import cn.lan.bookstore.entity.seller.StoreEntity;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.seller.IBookService;
import cn.lan.bookstore.service.seller.IStoreService;
import cn.lan.bookstore.vo.StoreInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/26/2018 08:36 AM
 */
@RequestMapping("/storeInfo")
@CrossOrigin("*")
@Slf4j
@RestController
public class StoreInfoController extends BaseController{

    @Autowired
    private IBookService bookService;
    @Autowired
    private IStoreService storeService;

    @RequestMapping("/baseInfo")
    public BaseResponse baseInfo(@RequestParam Long bookId){
        BookEntity bookEntity = bookService.findBookById(bookId);
        if (bookEntity == null) {
            return BaseResponse.ERROR;
        }
        StoreEntity storeEntity = storeService.findStoreById(bookEntity.getStoreId());
        StoreInfoVo storeInfoVo = new StoreInfoVo();
        BeanUtils.copyProperties(storeEntity, storeInfoVo);
        return new BaseResponse(true, storeInfoVo);

    }

}
