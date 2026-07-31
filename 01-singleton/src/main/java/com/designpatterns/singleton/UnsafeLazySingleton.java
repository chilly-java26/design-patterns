package com.designpatterns.singleton;

/**
 * 单例模式 - 懒汉式（线程不安全）
 * 仅用于演示，实际开发不要使用
 */
public class UnsafeLazySingleton {
    
    private static UnsafeLazySingleton instance = null;
    
    // 私有构造函数，防止外部实例化
    private UnsafeLazySingleton() {
        System.out.println("UnsafeLazySingleton 实例已创建");
    }
    
    // 线程不安全：多线程环境下可能创建多个实例
    public static UnsafeLazySingleton getInstance() {
        if (instance == null) {
            instance = new UnsafeLazySingleton();
        }
        return instance;
    }
    
    // 示例方法
    public void showMessage() {
        System.out.println("这是线程不安全的懒汉单例（仅演示用）");
    }
}
