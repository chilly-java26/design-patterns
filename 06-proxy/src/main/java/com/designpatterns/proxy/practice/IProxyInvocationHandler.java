package com.designpatterns.proxy.practice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IProxyInvocationHandler implements InvocationHandler {
    private Object target;

    public IProxyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("IProxyInvocationHandler invoking...");
        Object result = method.invoke(target, args);
        System.out.println("IProxyInvocationHandler invoked...");
        return result;
    }
}
