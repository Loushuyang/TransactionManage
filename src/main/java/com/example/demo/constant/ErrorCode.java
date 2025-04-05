package com.example.demo.constant;

public enum ErrorCode {

    SUCCESS(1000,"ok") ,
    PARAMS_ERROR(1001,"请求参数错误，交易不存在"),
    NULL_ERROR(1002,"请求参数为空"),
    NO_AUTH(1004,"暂无权限访问"),
    SYSTEM_ERROR(1005,"系统内部异常"),
    SELECT_ERROR(1006,"未查詢到交易"),
    BLOCK_ERROR(1007,"操作太頻繁"),
    EXIT_ERROR(1008, "重复插入"),
    ;
    //返回码
    private final int code;
    //操作响应信息
    private final String message;

    //构造函数
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    //get方法
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
