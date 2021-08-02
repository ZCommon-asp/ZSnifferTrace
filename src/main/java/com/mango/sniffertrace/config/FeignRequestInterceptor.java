package com.mango.sniffertrace.config;

import com.mango.sniffertrace.request.MDCTrace;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
* @description:
* @author: caozhibo
* @date: 2021/7/29 17:12
*/
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = MDCTrace.get();
        if (traceId != null) {
            requestTemplate.header(MDCTrace.HEADER_TRACE, traceId);
        }

    }
}
