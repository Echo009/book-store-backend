package cn.lan.bookstore.response;

import cn.lan.bookstore.enums.ResponseCodeEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 03:37 PM
 * @author Ech0
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BaseResponse<T> implements Serializable {


    public static final BaseResponse SUCCESS ;
    public static final BaseResponse ERROR;
    private static final long serialVersionUID = -3686652428349153708L;

    private Integer code;

    private String desc;

    private T data;

    public BaseResponse(Integer code, String desc,T data) {
        this.code=code;
        this.desc=desc;
        this.data=data;
    }
    public BaseResponse(ResponseCodeEnum codeEnum) {
        this.code=codeEnum.getCode();
        this.desc=codeEnum.getDesc();
    }
    static {
        SUCCESS = new BaseResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDesc(), null);
        ERROR = new BaseResponse(ResponseCodeEnum.ERROR.getCode(), ResponseCodeEnum.ERROR.getDesc(), null);
    }

    public BaseResponse(boolean isSuccess, T data) {

       this.data  = data ;
        if (isSuccess) {
            this.code = ResponseCodeEnum.SUCCESS.getCode();
            this.desc = ResponseCodeEnum.SUCCESS.getDesc();
        }else {
            this.code = ResponseCodeEnum.ERROR.getCode();
            this.desc = ResponseCodeEnum.ERROR.getDesc();
        }
    }
}
