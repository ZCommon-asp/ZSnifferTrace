package com.mango.sniffertrace.annotation.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.sniffertrace.annotation.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
     * 用于日志唯一id的保存
     */
    public static final ThreadLocal<String> LOG_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 定义切面
     */
    @Pointcut("@annotation(com.mango.sniffertrace.annotation.OperationLog)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        /*
         * 使用ThreadLocal保存处理请求线程的唯一id信息
         * 判断是否有唯一id，没有则生成一个并存至ThreadLocal
         */
        String uniqueId;
        if (LOG_THREAD_LOCAL.get() == null) {
            uniqueId = UUID.randomUUID().toString();
            LOG_THREAD_LOCAL.set(uniqueId);
        } else {
            uniqueId = LOG_THREAD_LOCAL.get();
        }

        // 方法开始时间
        long startTime = System.currentTimeMillis();


        // 获取方法签名
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);


        System.out.println("当前线程名：" + Thread.currentThread().getName());
        System.out.println(LOG_THREAD_LOCAL);

        if (operationLog.isEnter()) {

            log.info("----------------------------------------请求开始----------------------------------------");
            log.info("唯一id：" + uniqueId);
            log.info("接口描述：{}", operationLog.desc());
            // log.info("请求链接：{}", request.getRequestURL().toString());
            // log.info("请求类型：{}", request.getMethod());
            log.info("请求方法：{} - {}", signature.getDeclaringType(), signature.getName());
            // log.info("请求IP：{}", request.getRemoteAddr());
            log.info("请求入参：{}", objectMapper.writeValueAsString(pjp.getArgs()));
        } else {
            log.info("----------------------------------------方法开始----------------------------------------");
            log.info("唯一id：" + uniqueId);
            log.info("调用方法：{} - {}", signature.getDeclaringType(), signature.getName());
        }

        Object result = pjp.proceed();

        if (operationLog.isEnd()) {
            long endTime = System.currentTimeMillis();
            log.info("请求耗时：{}ms", (endTime - startTime));
            log.info("请求返回：{}", objectMapper.writeValueAsString(result));
            log.info("----------------------------------------请求结束----------------------------------------");

        } else {
            log.info("----------------------------------------方法结束----------------------------------------");
        }
        LOG_THREAD_LOCAL.remove();
        return result;
    }
}
