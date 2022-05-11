package com.dk.optimistic.interceptor;

import com.dk.optimistic.annotation.Idempotent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-10 20:51
 */
public class IdempotentInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ( !(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Idempotent annotation = method.getAnnotation(Idempotent.class);
        if (annotation != null){

            //幂等性校验
            checkToken(request);
        }
        return true;
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    private void checkToken(HttpServletRequest request) {

        //获取token
        String token = request.getHeader("token");

        if (StringUtils.isEmpty(token)){
            throw new RuntimeException("非法参数");
        }

        Boolean deleteResult = redisTemplate.delete(token);
        if (null == deleteResult) {
            return;
        }
        if (!deleteResult){
            //重复请求
            throw new RuntimeException("重复请求");
        }
    }
}