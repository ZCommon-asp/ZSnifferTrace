package com.aspire.sniffertrace.annotation.aop;

import com.aspire.sniffertrace.annotation.LogScan;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

/**
 * @description:
 * @author: caozhibo
 * @date: 2021/8/6 15:19
 */
public class LogSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        AnnotationAttributes mapperScansAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(LogScan.class.getName()));
        if (mapperScansAttrs != null) {
            String[] basePackages = (String[]) mapperScansAttrs.get("basePackages");

            LogScanAop.PACKAGES.addAll(Arrays.asList(basePackages));
        }
        return new String[0];
    }
}
