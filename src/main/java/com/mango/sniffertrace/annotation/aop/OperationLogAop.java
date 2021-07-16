package com.mango.sniffertrace.annotation.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.sniffertrace.annotation.OperationLog;
import com.mango.sniffertrace.request.LocalTrace;
import com.mango.sniffertrace.request.MDCTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @description:
 * @author: caozhibo
 * @date: 2021/6/24 11:31
 */
@Slf4j
@Aspect
@Component
public class OperationLogAop {

    @Resource
    private ObjectMapper objectMapper;


    /**
     * 定义切面
     */
    @Pointcut("@annotation(com.mango.sniffertrace.annotation.OperationLog)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 方法开始时间
        long startTime = System.currentTimeMillis();

        /*
         * MDC
         * 判断是否有唯一id，没有则生成一个并存至MDC
         */
        if ("".equals(MDCTrace.get())) {
            MDCTrace.put();
        }

        // 获取方法签名
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        OperationLog operationLog = method.getAnnotation(OperationLog.class);

        String header = "";
        String[] headerKey = operationLog.printHeader();
        if (headerKey.length > 0) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            if (null != request) {
                for (String key : headerKey) {
                    header += request.getHeader(key);
                }
            }
        }
        log.info("method name:{},declaringType:{},method desc:{},request header:{},request params:{}"
                , signature.getName()
                , signature.getDeclaringType()
                , operationLog.desc()
                , header
                , objectMapper.writeValueAsString(pjp.getArgs()));

        Object result = pjp.proceed();

        log.info("method name:{},declaringType:{},method desc:{},request header:{},request params:{},request result:{},request time consuming:{}"
                , signature.getName()
                , signature.getDeclaringType()
                , operationLog.desc()
                , header
                , objectMapper.writeValueAsString(pjp.getArgs())
                , objectMapper.writeValueAsString(result)
                , (System.currentTimeMillis() - startTime));

        LocalTrace.LOG_THREAD_LOCAL.remove();

        return result;
    }
}
