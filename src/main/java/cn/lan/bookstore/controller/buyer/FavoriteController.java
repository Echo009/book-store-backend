package cn.lan.bookstore.controller.buyer;

import cn.lan.bookstore.controller.BaseController;
import cn.lan.bookstore.entity.buyer.FavoriteEntity;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.buyer.IFavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/27/2018 02:31 AM
 */
@Slf4j
@RestController
@RequestMapping("/favorite")
@CrossOrigin("*")
public class FavoriteController extends BaseController {
    @Autowired
    private IFavoriteService favoriteService;

    @RequestMapping("/add")
    public BaseResponse add(boolean isBook, Long contentId) {
        FavoriteEntity favoriteEntity = favoriteService.add(isBook, contentId, getCurrentUserInfo().getUserId());
        return new BaseResponse(true, favoriteEntity);
    }

    @RequestMapping("/remove")
    public BaseResponse remove(Long id) {
        favoriteService.remove(getCurrentUserInfo().getUserId(), id);
        return new BaseResponse(true, null);
    }

    @RequestMapping("/all")
    public BaseResponse all(Boolean isBook) {

        List result = favoriteService.findAll(isBook, getCurrentUserInfo().getUserId());
        return new BaseResponse(true, result);
    }

}
