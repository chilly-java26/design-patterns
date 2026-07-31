package com.designpatterns.factory;

/**
 * 工厂方法模式示例 - 枚举单例版本
 * 演示两种工厂模式：
 * 1. 工厂方法模式：每个产品有独立的工厂枚举
 * 2. 简单工厂模式：通过 Class 对象创建实例（类似 LoggerFactory）
 */
public class Main {
    
    /**
     * 使用工厂创建形状并绘制
     * 
     * @param factory 工厂实例（枚举单例）
     */
    private static void createAndDraw(ShapeFactory factory) {
        Shape shape = factory.createShape();
        shape.draw();
    }
    
    public static void main(String[] args) {
        System.out.println("=== 工厂方法模式 + 枚举单例 ===\n");
        
        // 方式1：工厂方法模式 - 每个产品有独立的工厂
        System.out.println("--- 方式1：工厂方法模式 ---");
        createAndDraw(CircleFactory.INSTANCE);
        createAndDraw(RectangleFactory.INSTANCE);
        createAndDraw(SquareFactory.INSTANCE);
        createAndDraw(TriangleFactory.INSTANCE);
        
        // 方式2：简单工厂模式 - 通过 Class 创建（类似 LoggerFactory）
        System.out.println("\n--- 方式2：简单工厂模式（类似 LoggerFactory）---");
        
        // 使用静态方法（推荐）
        Shape shape = SimpleShapeFactory.getShape(Circle.class);
        shape.draw();
        
        shape = SimpleShapeFactory.getShape(Rectangle.class);
        shape.draw();
        
        shape = SimpleShapeFactory.getShape(Square.class);
        shape.draw();
        
        shape = SimpleShapeFactory.getShape(Triangle.class);
        shape.draw();
        
        // 验证是单例
        System.out.println("\n--- 验证枚举单例 ---");
        System.out.println("SimpleShapeFactory 是单例: " + 
            (SimpleShapeFactory.INSTANCE == SimpleShapeFactory.INSTANCE));
        
        System.out.println("\n=== 优点总结 ===");
        System.out.println("工厂方法模式：");
        System.out.println("  - 每个产品有独立的工厂枚举");
        System.out.println("  - 符合开闭原则，新增产品不修改现有代码");
        System.out.println("\n简单工厂模式：");
        System.out.println("  - 通过 Class 对象创建实例");
        System.out.println("  - 使用方式类似 LoggerFactory.getLogger(Class)");
        System.out.println("  - 枚举单例：线程安全、防止反射和序列化攻击");
    }
}
