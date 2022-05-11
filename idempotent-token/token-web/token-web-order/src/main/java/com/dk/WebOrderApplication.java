package com.dk;
import com.dk.interceptor.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-10 21:03
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.dk.order.feign"})
public class WebOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebOrderApplication.class,args);
    }


    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }
}