package com.mango.sniffertrace.request;

import com.mango.sniffertrace.util.DateUtil;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author: JacX.
 * @description:
 * @Date: 2021/7/16 10:30
 * @Modified By:JacX.
 * @see
 * @since
 */
public class MDCTrace {
    private static final String MDC_TRACE = "MDC_TRACE:";

    public static String get() {
        return MDC.get(MDC_TRACE);
    }

    public static void put() {
        MDC.put(MDC_TRACE, MDC_TRACE + DateUtil.getNow() + UUID.randomUUID());
    }
}
