package com.designpatterns.singleton;

/**
 * 单例模式 - 饿汉式（线程安全）
 * 类加载时就创建实例，简单且线程安全
 */
public class Singleton {
    
    // 在类加载时就创建实例
    private static final Singleton INSTANCE = new Singleton();
    
    // 私有构造函数，防止外部实例化
    private Singleton() {
        System.out.println("Singleton 实例已创建");
    }
    
    // 提供全局访问点
    public static Singleton getInstance() {
        return INSTANCE;
    }
    
    // 示例方法
    public void showMessage() {
        System.out.println("这是单例模式的示例方法");
    }
}
