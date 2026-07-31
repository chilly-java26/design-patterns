package com.designpatterns.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * 原型注册表（Prototype Registry）
 * 管理一组预定义的原型对象，客户端通过名称获取克隆
 */
public class PrototypeRegistryDemo {
    public static void main(String[] args) {
        System.out.println("=== 原型注册表演示 ===\n");
        
        // 1. 初始化原型注册表
        ShapeCache.loadCache();
        System.out.println("1. 原型注册表已初始化\n");
        
        // 2. 从注册表获取克隆对象
        System.out.println("2. 从注册表克隆图形:");
        Shape circle1 = ShapeCache.getShape("circle");
        circle1.draw();
        
        Shape rectangle1 = ShapeCache.getShape("rectangle");
        rectangle1.draw();
        
        Shape square1 = ShapeCache.getShape("square");
        square1.draw();
        System.out.println();
        
        // 3. 再次获取，验证是新对象
        System.out.println("3. 再次克隆，验证是新对象:");
        Shape circle2 = ShapeCache.getShape("circle");
        System.out.println("circle1 == circle2: " + (circle1 == circle2));
        System.out.println("都是Circle类型，但是不同的对象实例\n");
        
        // 4. 修改克隆对象
        System.out.println("4. 修改克隆对象的属性:");
        if (circle1 instanceof Circle) {
            ((Circle) circle1).setRadius(10);
            circle1.draw();
        }
        if (circle2 instanceof Circle) {
            ((Circle) circle2).setRadius(20);
            circle2.draw();
        }
    }
}

/**
 * 抽象原型
 */
abstract class Shape implements Cloneable {
    private String id;
    protected String type;
    
    public abstract void draw();
    
    public String getType() {
        return type;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * 具体原型：圆形
 */
class Circle extends Shape {
    private int radius = 5;
    
    public Circle() {
        type = "Circle";
    }
    
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.println("绘制圆形 [type=" + type + ", radius=" + radius + "]");
    }
}

/**
 * 具体原型：矩形
 */
class Rectangle extends Shape {
    public Rectangle() {
        type = "Rectangle";
    }
    
    @Override
    public void draw() {
        System.out.println("绘制矩形 [type=" + type + "]");
    }
}

/**
 * 具体原型：正方形
 */
class Square extends Shape {
    public Square() {
        type = "Square";
    }
    
    @Override
    public void draw() {
        System.out.println("绘制正方形 [type=" + type + "]");
    }
}

/**
 * 原型注册表：缓存并管理原型对象
 * 这是原型模式的一个重要扩展
 */
class ShapeCache {
    private static Map<String, Shape> shapeMap = new HashMap<>();
    
    /**
     * 通过ID获取图形的克隆
     */
    public static Shape getShape(String shapeId) {
        Shape cachedShape = shapeMap.get(shapeId);
        return cachedShape.clone();
    }
    
    /**
     * 加载预定义的原型到缓存
     * 实际应用中可能从数据库或配置文件加载
     */
    public static void loadCache() {
        Circle circle = new Circle();
        circle.setId("1");
        shapeMap.put("circle", circle);
        
        Rectangle rectangle = new Rectangle();
        rectangle.setId("2");
        shapeMap.put("rectangle", rectangle);
        
        Square square = new Square();
        square.setId("3");
        shapeMap.put("square", square);
    }
}
