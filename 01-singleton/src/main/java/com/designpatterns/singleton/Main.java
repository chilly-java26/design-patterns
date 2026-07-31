package com.designpatterns.singleton;

/**
 * 单例模式演示
 */
public class Main {
    
    public static void main(String[] args) {
        testSingleton();
        testLazySingleton();
    }

    private static void testSingleton() {
        System.out.println("=== 饿汉单例模式示例 ===\n");
        
        // 获取第一个实例
        System.out.println("获取第一个实例:");
        Singleton singleton1 = Singleton.getInstance();
        singleton1.showMessage();
        
        System.out.println();
        
        // 获取第二个实例
        System.out.println("获取第二个实例:");
        Singleton singleton2 = Singleton.getInstance();
        singleton2.showMessage();
        
        System.out.println();
        
        // 验证两个实例是否相同
        if (singleton1 == singleton2) {
            System.out.println("✓ singleton1 和 singleton2 是同一个实例");
            System.out.println("✓ 单例模式验证成功！");
        } else {
            System.out.println("✗ singleton1 和 singleton2 不是同一个实例");
        }
    }

    private static void testLazySingleton() {
        System.out.println("=== 懒汉单例模式示例 ===\n");
        
        // 获取第一个实例
        System.out.println("获取第一个实例:");
        Lazy singleton1 = Lazy.getInstance();
        singleton1.showMessage();
        
        System.out.println();
        
        // 获取第二个实例
        System.out.println("获取第二个实例:");
        Lazy singleton2 = Lazy.getInstance();
        singleton2.showMessage();
        
        System.out.println();
        
        // 验证两个实例是否相同
        if (singleton1 == singleton2) {
            System.out.println("✓ singleton1 和 singleton2 是同一个实例");
            System.out.println("✓ 单例模式验证成功！");
        } else {
            System.out.println("✗ singleton1 和 singleton2 不是同一个实例");
        }
    }
}
