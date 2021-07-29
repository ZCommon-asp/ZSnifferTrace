package com.mango.sniffertrace.request;

import cn.hutool.core.util.RandomUtil;
import com.mango.sniffertrace.util.DateUtil;
import org.slf4j.MDC;

import java.util.Random;
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
    public static final String MDC_TRACE = "MDC_TRACE:";

    public static final String HEADER_TRACE = "x-trace-id";

    public static String get() {
        return MDC.get(MDC_TRACE);
    }

    public static String getForLog() {
        String traceId = "";
        if (MDC.get(MDC_TRACE) != null) {
            traceId = "[" + MDC.get(MDC_TRACE) + "]";
        }
        return traceId;
    }

    public static void put() {
        MDC.put(MDC_TRACE, MDC_TRACE + DateUtil.getNow() + ":" + RandomUtil.randomString(3));
    }

    public static void put(String traceId) {
        MDC.put(MDC_TRACE, traceId);
    }
}
