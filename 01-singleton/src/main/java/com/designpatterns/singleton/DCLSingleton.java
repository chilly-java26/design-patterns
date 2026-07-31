package com.designpatterns.singleton;

/**
 * 单例模式 - 双重检查锁（Double-Checked Locking）
 * 懒加载 + 线程安全 + 高性能
 */
public class DCLSingleton {
    
    // volatile 防止指令重排序
    private static volatile DCLSingleton instance = null;
    
    // 私有构造函数，防止外部实例化
    private DCLSingleton() {
        System.out.println("DCLSingleton 实例已创建");
    }
    
    // 双重检查锁：只在首次创建时加锁
    public static DCLSingleton getInstance() {
        if (instance == null) {                    // 第一次检查：避免不必要的加锁
            synchronized (DCLSingleton.class) {    // 加锁
                if (instance == null) {            // 第二次检查：防止重复创建
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
    
    // 示例方法
    public void showMessage() {
        System.out.println("这是双重检查锁单例模式");
    }
}
