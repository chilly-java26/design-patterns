package com.designpatterns.singleton;

/**
 * 单例模式 - 懒汉式（线程安全）
 * 调用时才创建实例，简单且线程安全
 */
public class Lazy {

    // 懒汉模式：在类调用时创建实例
    private static class Holder {
        private static final Lazy INSTANCE = new Lazy();
    }

    // 私有构造函数，防止外部实例化
    private Lazy() {
        System.out.println("Singleton 懒汉实例已创建");
    }

    // 提供全局访问点
    public static Lazy getInstance() {
        return Holder.INSTANCE;
    }

    // 示例方法
    public void showMessage() {
        System.out.println("这是懒汉单例模式的示例方法");
    }
}
