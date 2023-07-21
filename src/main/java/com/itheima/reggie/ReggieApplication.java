package com.itheima.reggie;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//输出日志信息方便调试

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
//       log 对象 是@Slf4j 注解所提供的，作用是在控制台输出日志信息
        System.out.println("git代码提交");

        log.info("项目启动成功");
    }
}
