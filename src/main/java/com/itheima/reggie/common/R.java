package com.itheima.reggie.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;


/*
* 通用返回结果，服务端响应的数据都会最终封装返回此对象
* 返回的R类型的参数 取决于要给前端页面传输什么类型的数据
* public  R<Page> page(int page,int pageSize,String name){
*
*
* */
@Data
public class R<T> {
//    返回的状态码

    private Integer code; //编码：1成功，0和其它数字为失败
//    返回的错误信息
    private String msg; //错误信息
//     向前端页面返回的数据
    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
//    Exception

}
