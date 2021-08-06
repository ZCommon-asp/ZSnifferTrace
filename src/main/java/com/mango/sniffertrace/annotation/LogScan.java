package com.mango.sniffertrace.annotation;


import com.mango.sniffertrace.annotation.aop.LogSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({LogSelector.class})
@Documented
public @interface LogScan {

    String[] basePackages() default {};
}
