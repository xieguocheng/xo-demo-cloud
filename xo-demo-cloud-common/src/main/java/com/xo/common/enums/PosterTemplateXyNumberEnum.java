package com.xo.common.enums;

public enum PosterTemplateXyNumberEnum implements CodeEnum {
    AVATAR(1, "头像"), USERNAME(2, "用户名"), QRCODE(3, "二维码");
    private Integer code;
    private String message;

    PosterTemplateXyNumberEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
