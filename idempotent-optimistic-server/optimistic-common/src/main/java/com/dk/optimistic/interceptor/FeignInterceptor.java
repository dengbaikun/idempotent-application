package com.dk.optimistic.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-10 20:50
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        //传递令牌

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (null == request) {
            return;
        }
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()){

            String hearName = headerNames.nextElement();

            if ("token".equals(hearName)){

                String headerValue = request.getHeader(hearName);

                //传递token
                template.header(hearName,headerValue);
            }
        }
    }
}