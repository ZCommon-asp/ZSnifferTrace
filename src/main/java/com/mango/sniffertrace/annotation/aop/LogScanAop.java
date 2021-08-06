package com.mango.sniffertrace.annotation.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.sniffertrace.request.MDCTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: caozhibo
 * @date: 2021/8/5 18:15
 */
@Slf4j
@Aspect
@Configuration
public class LogScanAop {

    @Resource
    private ObjectMapper objectMapper;

    public static final List<String> PACKAGES = new ArrayList<>();


    @Pointcut("execution(public * com..controller.*.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        // 记录方法开始时间
        long startTime = System.currentTimeMillis();

        // 如果处理请求的线程已保存了TraceId，则不需要进行后续的处理流程
        if (MDCTrace.get() != null) {
            return pjp.proceed();
        }

        // 获取方法签名
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String packagePath = methodSignature.getDeclaringTypeName() + "." + signature.getName();


        boolean isOk = false;

        for (String path : PACKAGES) {
            if (path.length() <= packagePath.length()) {
                String prefix = packagePath.subSequence(0, path.length()).toString();
                if (path.equals(prefix)) {
                    isOk = true;
                    break;
                }
            }
        }

        if (!isOk) {
            return pjp.proceed();
        }


        // 从请求头获取日志唯一id
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String traceId = request.getHeader(MDCTrace.HEADER_TRACE);
            if (traceId != null && !"".equals(traceId)) {
                MDCTrace.put(traceId);
            }
        }

        /*
         * MDC
         * 判断是否有唯一id，没有则生成一个并存至MDC
         */
        if (MDCTrace.get() == null || "".equals(MDCTrace.get())) {
            MDCTrace.put();
        }

        log.info("method name:"+ signature.getName()
                + ",declaringType:" + signature.getDeclaringType()
                + ",request params:" + Arrays.toString(pjp.getArgs()));

        Object result = pjp.proceed();

        log.info("request result:" + objectMapper.writeValueAsString(result)
                + ",request time consuming:" + (System.currentTimeMillis() - startTime) + "ms");

        MDCTrace.remove();

        return result;
    }
}
