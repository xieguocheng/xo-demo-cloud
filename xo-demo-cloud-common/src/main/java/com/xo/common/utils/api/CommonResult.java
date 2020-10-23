package com.xo.common.utils.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CommonResult<T> {

    @ApiModelProperty(notes = "状态码（1：请求成功，0：请求错误，-1：服务器出现异常，-2：需要重新登录，-3：没有权限，-4：需要绑定手机号,-5：需要绑定微信号）")
    private Integer code;
    @ApiModelProperty
    private String message;
    @ApiModelProperty
    private T data;

    private CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResult serverError(String message) {
        return new CommonResult(ResultCode.SERVER_ERROR.getCode(), message);
    }

    public static CommonResult requestError(String message) {
        return new CommonResult(ResultCode.REQUEST_ERROR.getCode(), message);
    }

    public static <T> CommonResult<T> requestError(String message, T data) {
        return new CommonResult<>(ResultCode.REQUEST_ERROR.getCode(), message, data);
    }

    public static CommonResult unauthenticated(String message) {
        return new CommonResult(ResultCode.UNAUTHENTICATED.getCode(), message);
    }

    public static CommonResult unauthenticated() {
        return new CommonResult(ResultCode.UNAUTHENTICATED.getCode(), "请重新登录");
    }

    public static CommonResult unauthorized(String message) {
        return new CommonResult(ResultCode.UNAUTHORIZED.getCode(), message);
    }

    public static <T> CommonResult<T> bindPhone(T data) {
        return new CommonResult<>(ResultCode.BIND_PHONE.getCode(), "请先绑定手机号", data);
    }

    public static <T> CommonResult<T> bindWx() {
        return new CommonResult<>(ResultCode.BIND_WX.getCode(), "您还未绑定微信!请先前往我的->设置里选择微信号授权");
    }

    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ResultCode.REQUEST_SUCCESS.getCode(), message, data);
    }
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<>(ResultCode.REQUEST_SUCCESS.getCode(), message);
    }

    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        if (data != null) {
            jsonObject.put("data", data);
        }

        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }
}
