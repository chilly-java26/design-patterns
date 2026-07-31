package com.designpatterns.prototype;

/**
 * 原型模式演示
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== 原型模式演示 ===\n");
        
        // 1. 创建原始对象（耗时）
        System.out.println("1. 创建原始文档：");
        Document original = new Document("设计模式", "这是一篇关于设计模式的文档");
        System.out.println(original);
        System.out.println();
        
        // 2. 通过克隆创建新对象（快速）
        System.out.println("2. 克隆文档：");
        Document clone1 = original.clone();
        System.out.println(clone1);
        System.out.println();
        
        // 3. 修改克隆对象
        System.out.println("3. 修改克隆文档的标题：");
        clone1.setTitle("设计模式 - 副本1");
        System.out.println("原始文档: " + original);
        System.out.println("克隆文档: " + clone1);
        System.out.println();
        
        // 4. 再次克隆
        System.out.println("4. 再次克隆：");
        Document clone2 = original.clone();
        clone2.setTitle("设计模式 - 副本2");
        clone2.setContent("这是另一个克隆版本");
        System.out.println("原始文档: " + original);
        System.out.println("克隆文档1: " + clone1);
        System.out.println("克隆文档2: " + clone2);
        
        // 5. 验证对象独立性
        System.out.println("\n5. 验证对象独立性：");
        System.out.println("original == clone1: " + (original == clone1));
        System.out.println("original == clone2: " + (original == clone2));
    }
}
