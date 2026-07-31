package com.designpatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 静态代理 vs 动态代理对比演示
 */
public class StaticVsDynamicDemo {
    public static void main(String[] args) {
        System.out.println("=== 静态代理 vs 动态代理 ===\n");
        
        // ========== 静态代理 ==========
        System.out.println("【静态代理】");
        System.out.println("特点：代理类在编译期就已经确定\n");
        
        Image staticProxy = new ImageProxy("static_photo.jpg");
        staticProxy.display();
        System.out.println();
        
        // ========== 动态代理 ==========
        System.out.println("\n【动态代理】");
        System.out.println("特点：代理类在运行时动态生成\n");
        
        // 创建真实对象
        Image realImage = new RealImage("dynamic_photo.jpg");
        
        // 创建动态代理
        Image dynamicProxy = (Image) Proxy.newProxyInstance(
            Image.class.getClassLoader(),
            new Class<?>[]{Image.class},
            new LoggingHandler(realImage)
        );
        
        dynamicProxy.display();
        
        // ========== 对比总结 ==========
        System.out.println("\n\n=== 对比总结 ===");
        System.out.println("┌─────────────┬──────────────────┬──────────────────┐");
        System.out.println("│   特性      │   静态代理       │   动态代理       │");
        System.out.println("├─────────────┼──────────────────┼──────────────────┤");
        System.out.println("│ 代理类生成  │ 编译期手动编写   │ 运行时自动生成   │");
        System.out.println("│ 代理类数量  │ 一个接口一个代理 │ 一个Handler搞定  │");
        System.out.println("│ 灵活性      │ 低               │ 高               │");
        System.out.println("│ 代码量      │ 多               │ 少               │");
        System.out.println("│ 性能        │ 略快             │ 略慢（反射调用） │");
        System.out.println("│ 使用场景    │ 代理类少且固定   │ 需要代理多个类   │");
        System.out.println("└─────────────┴──────────────────┴──────────────────┘");
    }
}

/**
 * 通用的日志处理器
 * 可以代理任何接口！
 */
class LoggingHandler implements InvocationHandler {
    private Object target;
    
    public LoggingHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("[LOG] 方法调用: " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("[LOG] 方法返回");
        return result;
    }
}
