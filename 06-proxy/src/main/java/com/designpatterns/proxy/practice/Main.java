package com.designpatterns.proxy.practice;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {

        IProxyImpl impl = new IProxyImpl();

        IProxy proxy = (IProxy) Proxy.newProxyInstance(
                IProxy.class.getClassLoader(),
                new Class<?>[]{IProxy.class},
                new IProxyInvocationHandler(impl)
        );

        proxy.method();
    }
}
