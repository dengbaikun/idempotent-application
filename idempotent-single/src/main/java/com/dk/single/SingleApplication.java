package com.dk.single;

import com.dk.single.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 21:07
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.dk.single.dao"})
public class SingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SingleApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}

