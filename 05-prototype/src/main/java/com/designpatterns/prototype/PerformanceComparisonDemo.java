package com.designpatterns.prototype;

/**
 * 原型模式性能对比
 * 展示原型模式的核心优势：创建对象的性能提升
 */
public class PerformanceComparisonDemo {
    public static void main(String[] args) {
        System.out.println("=== 原型模式性能对比 ===\n");
        
        int iterations = 1000;
        
        // 1. 测试通过构造函数创建对象
        System.out.println("1. 通过构造函数创建 " + iterations + " 个对象:");
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            ExpensiveObject obj = new ExpensiveObject("对象" + i);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("耗时: " + (end1 - start1) + "ms\n");
        
        // 2. 测试通过克隆创建对象
        System.out.println("2. 通过克隆创建 " + iterations + " 个对象:");
        ExpensiveObject prototype = new ExpensiveObject("原型对象");
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            ExpensiveObject obj = prototype.clone();
            obj.setName("对象" + i);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("耗时: " + (end2 - start2) + "ms\n");
        
        // 3. 性能对比
        System.out.println("3. 性能对比:");
        long constructorTime = end1 - start1;
        long cloneTime = end2 - start2;
        System.out.println("构造函数方式: " + constructorTime + "ms");
        System.out.println("克隆方式: " + cloneTime + "ms");
        if (constructorTime > cloneTime) {
            System.out.println("克隆方式快 " + (constructorTime - cloneTime) + "ms");
            System.out.println("性能提升: " + String.format("%.2f", 
                (double)(constructorTime - cloneTime) / constructorTime * 100) + "%");
        }
    }
}

/**
 * 模拟创建成本较高的对象
 */
class ExpensiveObject implements Cloneable {
    private String name;
    private byte[] data;
    
    public ExpensiveObject(String name) {
        this.name = name;
        // 模拟耗时的初始化操作
        // 比如：从数据库加载数据、复杂计算、网络请求等
        this.data = new byte[1024 * 10]; // 10KB数据
        try {
            Thread.sleep(1); // 模拟耗时操作
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public ExpensiveObject clone() {
        try {
            return (ExpensiveObject) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
