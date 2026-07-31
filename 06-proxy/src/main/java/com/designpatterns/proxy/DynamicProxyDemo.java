package com.designpatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理演示
 * 使用 JDK 动态代理（基于接口）
 */
public class DynamicProxyDemo {
    public static void main(String[] args) {
        System.out.println("=== 动态代理演示 ===\n");
        
        // 1. 创建真实对象
        Image realImage = new RealImage("dynamic_photo.jpg");
        System.out.println();
        
        // 2. 创建动态代理
        System.out.println("创建动态代理...");
        Image proxyImage = (Image) Proxy.newProxyInstance(
            realImage.getClass().getClassLoader(),  // 类加载器
            realImage.getClass().getInterfaces(),   // 接口列表
            new ImageInvocationHandler(realImage)   // 调用处理器
        );
        System.out.println("动态代理创建完成\n");
        
        // 3. 通过代理调用方法
        System.out.println("通过动态代理调用 display():");
        proxyImage.display();
        System.out.println();
        
        System.out.println("再次调用:");
        proxyImage.display();
    }
}

/**
 * 调用处理器
 * 所有通过代理的方法调用都会经过这里
 */
class ImageInvocationHandler implements InvocationHandler {
    private Object target; // 真实对象
    
    public ImageInvocationHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置增强：方法调用前执行
        System.out.println(">>> [动态代理] 调用方法: " + method.getName());
        long startTime = System.currentTimeMillis();
        
        // 调用真实对象的方法
        Object result = method.invoke(target, args);
        
        // 后置增强：方法调用后执行
        long endTime = System.currentTimeMillis();
        System.out.println(">>> [动态代理] 方法执行完成，耗时: " + (endTime - startTime) + "ms");
        
        return result;
    }
}
