package com.designpatterns.singleton;

import java.io.Serializable;

/**
 * 单例模式 - 懒汉式（线程安全）
 * 使用静态内部类实现懒加载
 * 防御反射和反序列化攻击
 */
public class LazySingleton implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 标志位：防止反射攻击
    private static boolean initialized = false;
    
    // 私有构造函数，防止外部实例化
    private LazySingleton() {
        synchronized (LazySingleton.class) {
            if (initialized) {
                throw new RuntimeException("单例已存在，禁止通过反射创建实例！");
            }
            initialized = true;
            System.out.println("LazySingleton 实例已创建");
        }
    }
    
    // 静态内部类，只在首次调用 getInstance() 时加载
    private static class Holder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
    
    // 提供全局访问点
    public static LazySingleton getInstance() {
        return Holder.INSTANCE;
    }
    
    // 防止反序列化创建新实例
    private Object readResolve() {
        System.out.println("readResolve() 被调用，返回已有实例");
        return Holder.INSTANCE;
    }
    
    // 示例方法
    public void showMessage() {
        System.out.println("这是懒汉单例模式的示例方法");
    }
}
