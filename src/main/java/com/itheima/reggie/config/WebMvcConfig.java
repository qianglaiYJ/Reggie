package com.itheima.reggie.config;

import com.itheima.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Arrays;
import java.util.List;

/*
* 为了浏览器找到resources下的前端资源，编写对应的配置类
* 配置类要继承 WebMvcConfigurationSupport，并重写对应的方法
* 配置类要用注解进行标识
* */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
//  addResourceHandlers ，进行静态资源映射的
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源映射");
//        addResourceHandler 参数是浏览器访问的路径 /backed/** 代表backed 下的任意资源
//        addResourceLocations classpath 对应的source 目录
        /*
        * 方法说明：
        * 请求的路径，要与映射的路径的父目录要一致，如果不一致，会出现404 映射资源错误
        * 以及注意 addResourceLocations 方法参数 ，classpath: 指的是是resource 目录 以及注意对应的目录后面要有/
        *
        * /backend/** 与 classpath:/backend/page/ 会报404
        * */
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");

    }

    /**
     * 扩展mvc 框架的消息转换器,项目启动的时候调用
     * @param converters
     */

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");
//        创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//      设置对象转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());

//        将上面的消息转换器对象追加到mvc框架的转换器集合当中
        converters.add(0,messageConverter);
    }
}
