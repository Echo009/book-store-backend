package cn.lan.bookstore.exception;

import cn.lan.bookstore.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/20/2018 12:26 AM
 */
@Data
public class BaseServerException extends RuntimeException{

    private Integer code;

    public BaseServerException() {

    }

    public BaseServerException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getDesc());
        this.code = responseCodeEnum.getCode();
    }

    public BaseServerException(Integer code, String msg) {
        super(msg);
        this.code =code;
    }
}
