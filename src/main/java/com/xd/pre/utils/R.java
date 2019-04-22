package com.xd.pre.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


/**
 * @Classname R
 * @Description 响应信息主体
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-03-27 21:54
 * @Version 1.0
 */
@Setter
@Getter
@Accessors(chain = true)
public class R implements Serializable {

    private static final long serialVersionUID = 1L;


    private int code = 200;
    private String msg;
    private Object data;

    public static R ok(String msg) {
        R r = new R();
        r.setMsg(msg);
        return r;
    }

    public static R ok(Object data) {
        R r = new R();
        r.setData(data);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    private static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }



}
