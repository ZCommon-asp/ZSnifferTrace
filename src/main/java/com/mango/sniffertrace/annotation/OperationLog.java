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
     * 是否为入口位置
     */
    boolean isEnter() default false;

    /**
     * 是否为结束位置
     */
    boolean isEnd() default true;

    /**
     * 描述
     */
    String desc() default "";
}
