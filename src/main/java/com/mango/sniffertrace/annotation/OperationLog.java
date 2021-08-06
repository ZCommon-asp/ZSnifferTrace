package com.mango.sniffertrace.annotation;


import java.lang.annotation.*;

/**
 * 分布式日志增强注解
 * 提供分布式日志调用链路唯一id
 *
 * @author caozhibo
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /**
     * 打印请求头参数
     * @return
     */
    String[] printHeader() default "";

    /**
     * 描述
     */
    String desc() default "";
}
