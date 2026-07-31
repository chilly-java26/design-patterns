# 工厂方法模式 (Factory Method Pattern)

## 简介
工厂方法模式定义了一个创建对象的接口，但由实现类决定要实例化的类是哪一个。本项目演示了两种工厂模式：
1. **工厂方法模式**：每个产品有独立的工厂枚举（枚举单例）
2. **简单工厂模式**：通过 Class 对象创建实例（类似 LoggerFactory）

## 核心特点
✨ **枚举单例**：所有工厂类都使用枚举实现单例模式  
✨ **双模式演示**：同时展示工厂方法和简单工厂两种设计  
✨ **类型安全**：通过泛型保证返回值类型  

## 类结构

### 产品层次
- **Shape (接口)**: 产品抽象接口
- **Circle**: 圆形
- **Rectangle**: 矩形
- **Square**: 正方形
- **Triangle**: 三角形（演示扩展性）

### 工厂方法模式
每个产品有独立的工厂枚举：
- **ShapeFactory (接口)**: 定义工厂方法 `createShape()`
- **CircleFactory (enum)**: 创建 Circle
- **RectangleFactory (enum)**: 创建 Rectangle
- **SquareFactory (enum)**: 创建 Square
- **TriangleFactory (enum)**: 创建 Triangle

### 简单工厂模式
- **SimpleShapeFactory (enum)**: 通过反射根据 Class 创建实例

## 使用示例

### 方式 1：工厂方法模式（枚举单例）

```java
// 使用枚举工厂的 INSTANCE
Shape circle = CircleFactory.INSTANCE.createShape();
circle.draw();

// 统一处理
private static void createAndDraw(ShapeFactory factory) {
    Shape shape = factory.createShape();
    shape.draw();
}

createAndDraw(CircleFactory.INSTANCE);
createAndDraw(TriangleFactory.INSTANCE);
```

### 方式 2：简单工厂模式（类似 LoggerFactory）

```java
// 静态方法，类似 LoggerFactory.getLogger(Class)
Circle circle = SimpleShapeFactory.getShape(Circle.class);
circle.draw();

Rectangle rectangle = SimpleShapeFactory.getShape(Rectangle.class);
rectangle.draw();
```

## 新增产品流程

### 工厂方法模式
添加新产品（如 Pentagon）需要两步：

1. **创建产品类**：实现 `Shape` 接口
```java
public class Pentagon implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing: Pentagon");
    }
}
```

2. **创建工厂枚举**：实现 `ShapeFactory` 接口
```java
public enum PentagonFactory implements ShapeFactory {
    INSTANCE;
    
    @Override
    public Shape createShape() {
        return new Pentagon();
    }
}
```

### 简单工厂模式
只需创建产品类，无需创建工厂：

```java
// 1. 创建产品类
public class Pentagon implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing: Pentagon");
    }
}

// 2. 直接使用，无需其他代码
Pentagon pentagon = SimpleShapeFactory.getShape(Pentagon.class);
pentagon.draw();
```

## 运行示例

```bash
cd 02-factory
mvn clean compile exec:java -Dexec.mainClass="com.designpatterns.factory.Main"
```

## 输出示例
```
=== 工厂方法模式 + 枚举单例 ===

--- 方式1：工厂方法模式 ---
Drawing: Circle
Drawing: Rectangle
Drawing: Square
Drawing: Triangle

--- 方式2：简单工厂模式（类似 LoggerFactory）---
Drawing: Circle
Drawing: Rectangle
Drawing: Square
Drawing: Triangle

--- 验证枚举单例 ---
SimpleShapeFactory 是单例: true
```

## 模式对比

### 工厂方法 vs 简单工厂

| 特性 | 工厂方法模式 | 简单工厂模式 |
|------|-------------|-------------|
| 工厂数量 | 多个工厂类 | 1 个工厂类 |
| 新增产品 | 需添加产品类 + 工厂类 | 只需添加产品类 |
| 开闭原则 | ✓ 完全符合 | ✓ 符合（使用反射） |
| 使用方式 | `Factory.INSTANCE.createShape()` | `Factory.getShape(Class)` |
| 类型安全 | ✓ 编译期检查 | ✓ 泛型保证 |
| 适用场景 | 需要不同工厂逻辑 | 创建逻辑简单统一 |

### 枚举单例 vs 其他单例

| 特性 | 枚举单例 | 静态内部类 | 双重检查锁 |
|------|---------|-----------|-----------|
| 线程安全 | ✓ JVM 保证 | ✓ JVM 保证 | ✓ volatile |
| 防反射攻击 | ✓ 天然防护 | ✗ 需额外处理 | ✗ 需额外处理 |
| 防序列化攻击 | ✓ 天然防护 | ✗ 需 readResolve | ✗ 需 readResolve |
| 代码简洁性 | ✓ 最简单 | ✓ 简单 | ✗ 复杂 |
| 推荐程度 | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐ |

## 优点总结

### 工厂方法模式 + 枚举单例
✅ **线程安全**：枚举天然线程安全  
✅ **防止攻击**：防反射、防序列化攻击  
✅ **符合 OCP**：新增产品不修改现有代码  
✅ **单一职责**：每个工厂只负责一种产品  
✅ **代码简洁**：枚举单例只需一行声明  

### 简单工厂模式 + 反射
✅ **极简扩展**：新增产品无需创建工厂类  
✅ **类型安全**：泛型返回值，无需强转  
✅ **使用方便**：类似 LoggerFactory 的习惯  
✅ **枚举单例**：全局唯一工厂实例  

## 适用场景

### 使用工厂方法模式
- 需要不同的创建逻辑（如从不同数据源）
- 希望工厂本身可扩展
- 强调单一职责和开闭原则

### 使用简单工厂模式
- 创建逻辑简单统一（通过反射即可）
- 追求极简的扩展方式
- 类似框架式的使用习惯（LoggerFactory）

## 相关模式
- **单例模式**：本项目所有工厂都是枚举单例
- **抽象工厂**：创建一系列相关或依赖的产品族
- **Spring Boot + 配置驱动工厂**：参见 `03-springboot-drawshape` 项目
