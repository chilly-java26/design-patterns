package com.designpatterns.singleton;

/**
 * 单例模式 - 懒汉式（synchronized 方法）
 * 线程安全但性能较差：每次获取实例都要加锁
 */
public class SynchronizedLazySingleton {
    
    private static SynchronizedLazySingleton instance = null;
    
    // 私有构造函数，防止外部实例化
    private SynchronizedLazySingleton() {
        System.out.println("SynchronizedLazySingleton 实例已创建");
    }
    
    // synchronized 保证线程安全，但每次调用都要加锁，性能差
    public static synchronized SynchronizedLazySingleton getInstance() {
        if (instance == null) {
            instance = new SynchronizedLazySingleton();
        }
        return instance;
    }
    
    // 示例方法
    public void showMessage() {
        System.out.println("这是 synchronized 方法的懒汉单例（线程安全但性能差）");
    }
}
