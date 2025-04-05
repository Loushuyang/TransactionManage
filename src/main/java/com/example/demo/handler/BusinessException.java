package com.example.demo.handler;


import com.example.demo.constant.ErrorCode;

/**
 * 业务异常类
 * 继承RuntimeException异常处理类。
 */
public class BusinessException extends RuntimeException{
    private  int code;
    private  String message;

    /**
     * 各种构造函数，供我们灵活的使用
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
