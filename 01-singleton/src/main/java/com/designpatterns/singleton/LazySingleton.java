package com.designpatterns.singleton;

/**
 * 单例模式 - 懒汉式（线程安全）
 * 使用静态内部类实现懒加载
 */
public class LazySingleton {
    
    // 私有构造函数，防止外部实例化
    private LazySingleton() {
        System.out.println("LazySingleton 实例已创建");
    }
    
    // 静态内部类，只在首次调用 getInstance() 时加载
    private static class Holder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
    
    // 提供全局访问点
    public static LazySingleton getInstance() {
        return Holder.INSTANCE;
    }
    
    // 示例方法
    public void showMessage() {
        System.out.println("这是懒汉单例模式的示例方法");
    }
}
