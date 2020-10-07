package com.aifei.response;

public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(0, "操作成功！"),

    /* 错误状态码 */
    FAIL(-1, "操作失败！");

    // 操作代码
    int code;
    // 提示信息
    String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}