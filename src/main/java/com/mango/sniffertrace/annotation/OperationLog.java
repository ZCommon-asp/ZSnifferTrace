package com.mango.sniffertrace.annotation;


import java.lang.annotation.*;

/**
 * @author lenovo
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /**
     * 打印请求头参数
     * @return
     */
    String[] printHeader();

    /**
     * 描述
     */
    String desc() default "";
}
