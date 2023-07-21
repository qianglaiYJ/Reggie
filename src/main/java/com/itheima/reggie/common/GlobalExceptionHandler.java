package com.itheima.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/*
*@ControllerAdvice注解的用法
* 用于捕获全局异常，如果捕获所有Controller 异常，包括RestController，Controller
* 需要给annotations （是一个数组形式）复值
*
* 且捕获全局异常类以字符串形式输出用@ResponseBody 注解输出信息
*
*
* 且用于异常处理的方法用
* @Exception 注解标识
*
*
* */

@ControllerAdvice(annotations ={RestController.class, Controller.class} )
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /*
    * 异常处理方法
    * */
    @ExceptionHandler
    public  R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return  R.error(msg);
        }
        return R.error("未知错误");

    }




}
