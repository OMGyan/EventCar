package com.yx.eventcarlib;

import java.lang.reflect.Method;

/**
 * Author by YX, Date on 2019/8/6.
 * 订阅方法封装
 */
public class MethodManager {
    private Method method;

    private Class<?> type;

    private ThreadMode threadMode;

    public MethodManager(Method method, Class<?> type, ThreadMode threadMode) {
        this.method = method;
        this.type = type;
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getType() {
        return type;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }
}
