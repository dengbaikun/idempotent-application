package com.dk.order.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 23:00
 */
@Configuration
public class FeignConfigure {

    //超时时间，时间单位毫秒
    public static int connectTimeOutMillis = 5000;
    public static int readTimeOutMillis = 5000;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    //自定义重试次数
    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(100, 1000, 4);
    }
}