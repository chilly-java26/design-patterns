package com.designpatterns.singleton;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * 单例模式演示
 */
public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 单例模式示例 ===\n");
        
        // 测试饿汉式
        System.out.println("【1. 饿汉式单例】");
        Singleton singleton1 = Singleton.getInstance();
        singleton1.showMessage();
        Singleton singleton2 = Singleton.getInstance();
        singleton2.showMessage();
        System.out.println("验证: " + (singleton1 == singleton2 ? "✓ 同一实例" : "✗ 不同实例"));
        
        System.out.println("\n----------------------------------------\n");
        
        // 测试懒汉式（线程安全）
        System.out.println("【2. 懒汉式单例（线程安全）】");
        LazySingleton lazy1 = LazySingleton.getInstance();
        lazy1.showMessage();
        LazySingleton lazy2 = LazySingleton.getInstance();
        lazy2.showMessage();
        System.out.println("验证: " + (lazy1 == lazy2 ? "✓ 同一实例" : "✗ 不同实例"));
        
        System.out.println("\n----------------------------------------\n");
        
        // 测试线程不安全的懒汉式
        System.out.println("【3. 懒汉式单例（线程不安全）- 多线程测试】");
        testUnsafeSingleton();
        
        System.out.println("\n----------------------------------------\n");
        
        // 测试 synchronized 方法的懒汉式
        System.out.println("【4. synchronized 方法懒汉式单例】");
        SynchronizedLazySingleton sync1 = SynchronizedLazySingleton.getInstance();
        sync1.showMessage();
        SynchronizedLazySingleton sync2 = SynchronizedLazySingleton.getInstance();
        System.out.println("验证: " + (sync1 == sync2 ? "✓ 同一实例" : "✗ 不同实例"));
        
        System.out.println("\n----------------------------------------\n");
        
        // 测试双重检查锁
        System.out.println("【5. 双重检查锁单例】");
        DCLSingleton dcl1 = DCLSingleton.getInstance();
        dcl1.showMessage();
        DCLSingleton dcl2 = DCLSingleton.getInstance();
        System.out.println("验证: " + (dcl1 == dcl2 ? "✓ 同一实例" : "✗ 不同实例"));
        
        System.out.println("\n----------------------------------------\n");
        
        // 性能对比测试
        System.out.println("【6. 性能对比测试】");
        performanceComparison();
        
        System.out.println("\n✓ 测试完成！");
    }
    
    /**
     * 测试线程不安全的单例模式
     * 使用多线程并发访问，尝试暴露线程安全问题
     */
    private static void testUnsafeSingleton() throws InterruptedException {
        final int threadCount = 100;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        final Set<UnsafeLazySingleton> instances = new HashSet<UnsafeLazySingleton>();
        
        System.out.println("启动 " + threadCount + " 个线程同时获取实例...\n");
        
        // 创建多个线程同时获取实例
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                public void run() {
                    UnsafeLazySingleton instance = UnsafeLazySingleton.getInstance();
                    synchronized (instances) {
                        instances.add(instance);
                    }
                    latch.countDown();
                }
            }).start();
        }
        
        // 等待所有线程完成
        latch.await();
        
        // 检查结果
        System.out.println("创建的实例数量: " + instances.size());
        if (instances.size() == 1) {
            System.out.println("验证: ✓ 只有一个实例（运气好，没出现线程安全问题）");
        } else {
            System.out.println("验证: ✗ 创建了多个实例！线程不安全！");
        }
    }
    
    /**
     * 性能对比测试：静态内部类 vs synchronized 方法 vs 双重检查锁
     */
    private static void performanceComparison() throws InterruptedException {
        final int threadCount = 10;
        final int callsPerThread = 100000;
        
        System.out.println("测试条件: " + threadCount + " 个线程，每个线程调用 " + callsPerThread + " 次\n");
        
        // 预热：先调用一次，确保实例已创建
        LazySingleton.getInstance();
        SynchronizedLazySingleton.getInstance();
        DCLSingleton.getInstance();
        
        // 测试静态内部类版本
        long time1 = testPerformance("静态内部类", threadCount, callsPerThread, new Runnable() {
            public void run() {
                LazySingleton.getInstance();
            }
        });
        
        // 测试 synchronized 方法版本
        long time2 = testPerformance("synchronized方法", threadCount, callsPerThread, new Runnable() {
            public void run() {
                SynchronizedLazySingleton.getInstance();
            }
        });
        
        // 测试双重检查锁版本
        long time3 = testPerformance("双重检查锁", threadCount, callsPerThread, new Runnable() {
            public void run() {
                DCLSingleton.getInstance();
            }
        });
        
        // 对比结果
        System.out.println("\n性能对比:");
        System.out.println("  静态内部类:       " + time1 + " ms  (基准)");
        System.out.println("  双重检查锁:       " + time3 + " ms  (" + String.format("%.2f", (double) time3 / time1) + "倍)");
        System.out.println("  synchronized方法: " + time2 + " ms  (" + String.format("%.2f", (double) time2 / time1) + "倍)");
        System.out.println("\n结论: 静态内部类和双重检查锁性能接近，都远优于synchronized方法");
    }
    
    /**
     * 测试单例模式性能
     */
    private static long testPerformance(final String name, int threadCount, final int callsPerThread, final Runnable task) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(threadCount);
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < callsPerThread; j++) {
                        task.run();
                    }
                    latch.countDown();
                }
            }).start();
        }
        
        latch.await();
        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        
        System.out.println(name + " 耗时: " + elapsed + " ms");
        
        return elapsed;
    }
}
