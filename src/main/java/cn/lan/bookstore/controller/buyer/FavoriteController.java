package cn.lan.bookstore.controller.buyer;

import cn.lan.bookstore.controller.BaseController;
import cn.lan.bookstore.entity.buyer.FavoriteEntity;
import cn.lan.bookstore.enums.buyer.FavoriteTypeEnum;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.buyer.IFavoriteService;
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
        // 添加失败则说明为重复添加
        FavoriteEntity favoriteEntity;
        try {
            favoriteEntity  = favoriteService.add(isBook, contentId, getCurrentUserInfo().getUserId());
        } catch (Exception e) {
            return new BaseResponse(false, "请勿重复添加");
        }

        return new BaseResponse(true, favoriteEntity);
    }

    @RequestMapping("/remove")
    public BaseResponse remove(@RequestParam(required = false) Long id,
                               @RequestParam(required = false) Boolean isBook,
                               @RequestParam(required = false) Long contentId) {
        if (id != null) {
            favoriteService.remove(getCurrentUserInfo().getUserId(), id);
        } else {
            if (isBook != null) {
                if (isBook) {
                    favoriteService.remove(
                            getCurrentUserInfo().getUserId(),
                            Short.valueOf(FavoriteTypeEnum.BOOK.getCode() + ""),
                            contentId);
                } else {
                    favoriteService.remove(
                            getCurrentUserInfo().getUserId(),
                            Short.valueOf(FavoriteTypeEnum.STORE.getCode() + ""),
                            contentId);
                }
            } else {
                return new BaseResponse(false, ResponseCodeEnum.ILLEGAL_ARGUMENT);
            }
        }
        return new BaseResponse(true, null);
    }

    @RequestMapping("/all")
    public BaseResponse all(Boolean isBook) {

        List result = favoriteService.findAll(isBook, getCurrentUserInfo().getUserId());
        return new BaseResponse(true, result);
    }

    @RequestMapping("/check")
    public BaseResponse check(Boolean isBook,Long contentId) {

        if (favoriteService.check(isBook, contentId, getCurrentUserInfo().getUserId())) {
            // 已经收藏
            return BaseResponse.SUCCESS;
        }
        return BaseResponse.ERROR;
    }
}
