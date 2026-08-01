package com.designpatterns.proxy.practice;

public class IProxyImpl implements IProxy {
    @Override
    public void method() {
        System.out.println("called method()");
    }
}
