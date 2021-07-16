package com.mango.sniffertrace.request;

/**
 * @author: JacX.
 * @description:
 * @Date: 2021/7/15 20:07
 * @Modified By:JacX.
 * @see
 * @since
 */
public class LocalTrace {
    /**
     * 用于日志唯一id的保存
     */
    public static final ThreadLocal<String> LOG_THREAD_LOCAL = new ThreadLocal<>();

    public static String getTraceId() {
        String id = LOG_THREAD_LOCAL.get();
        if (id != null) {
            return id + "-";
        }
        return "";
    }
}
