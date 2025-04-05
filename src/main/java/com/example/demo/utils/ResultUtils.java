package com.example.demo.utils;

import com.example.demo.constant.ErrorCode;
import com.example.demo.domain.BaseResponse;

public class ResultUtils {
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(1000, data, "成功");
    }

    /**
     * 成功
     * @param
     * @param
     * @return
     */
    public static  BaseResponse success() {
        return new BaseResponse<>(1000, "成功");
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static BaseResponse success(int data) {
        return new BaseResponse(1000, data, "成功");
    }


    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String message,String description){
        return new BaseResponse<>(errorCode.getCode(), description)   ;
    }

    /**
     * 失败
     * @param code
     * @return
     */
    public static BaseResponse error(int code, String message){
        return new BaseResponse<>(code,null, message)   ;
    }
    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage());
    }
}
