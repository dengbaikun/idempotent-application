package com.dk.order;

import com.dk.redis.interceptor.IdempotentInterceptor;
import com.dk.redis.util.IdWorker;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-10 21:16
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.dk.order.dao"})
@EnableFeignClients(basePackages = {"com.dk.stock.feign"})
public class OrderApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

    /**
     * 设置 redisTemplate 的序列化设置
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 1.创建 redisTemplate 模版
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 2.关联 redisConnectionFactory
        template.setConnectionFactory(redisConnectionFactory);
        // 3.创建 序列化类
        GenericToStringSerializer<Object> genericToStringSerializer = new GenericToStringSerializer<>(Object.class);
        // 6.序列化类，对象映射设置
        // 7.设置 value 的转化格式和 key 的转化格式
        template.setValueSerializer(genericToStringSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public IdempotentInterceptor idempotentInterceptor(){
        return new IdempotentInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentInterceptor());
        super.addInterceptors(registry);
    }

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    @Bean
    public RedissonClient redissonClient(){

        RedissonClient redissonClient;

        Config config = new Config();
        String url = "redis://"+host+":"+port;
        config.useSingleServer().setAddress(url).setPassword(password);
        config.setLockWatchdogTimeout(3000L);
        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
