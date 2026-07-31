# 工厂模式合集

本项目演示了三种工厂模式：
1. **工厂方法模式**：每个产品有独立的工厂
2. **简单工厂模式**：一个工厂创建所有产品
3. **抽象工厂模式**：创建一系列相关产品的家族

---

## 1. 工厂方法模式 (Factory Method Pattern)

### 简介
工厂方法模式定义了一个创建对象的接口，但由实现类决定要实例化的类是哪一个。本项目所有工厂都使用**枚举单例**实现。  

### 类结构

#### 产品层次
- **Shape (接口)**: 产品抽象接口
- **Circle**: 圆形
- **Rectangle**: 矩形
- **Square**: 正方形
- **Triangle**: 三角形（演示扩展性）

#### 工厂方法模式
每个产品有独立的工厂枚举：
- **ShapeFactory (接口)**: 定义工厂方法 `createShape()`
- **CircleFactory (enum)**: 创建 Circle
- **RectangleFactory (enum)**: 创建 Rectangle
- **SquareFactory (enum)**: 创建 Square
- **TriangleFactory (enum)**: 创建 Triangle

---

## 2. 简单工厂模式 (Simple Factory Pattern)

### 简介
简单工厂模式使用一个工厂类创建所有产品，通过反射和 Class 对象实现。使用方式类似 `LoggerFactory.getLogger(Class)`。

### 简单工厂类
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
- **抽象工厂**：见下文，创建一系列相关或依赖的产品族
- **Spring Boot + 配置驱动工厂**：参见 `03-springboot-drawshape` 项目

---

## 3. 抽象工厂模式 (Abstract Factory Pattern)

### 简介
抽象工厂模式提供一个接口，用于创建**相关或依赖对象的家族**，而不需要明确指定具体类。

### 核心思想
- **产品族**：一组相关的产品（如 Windows 风格的按钮和文本框）
- **抽象工厂**：定义创建整个产品族的接口
- **具体工厂**：实现抽象工厂，创建具体产品族
- **保证一致性**：同一工厂创建的产品风格统一

### 类结构

#### 抽象产品
- **Button (接口)**: 按钮抽象
- **TextField (接口)**: 文本框抽象

#### 具体产品
- **WindowsButton**: Windows 风格按钮
- **WindowsTextField**: Windows 风格文本框
- **MacButton**: Mac 风格按钮
- **MacTextField**: Mac 风格文本框

#### 抽象工厂
- **UIFactory (接口)**: 定义创建按钮和文本框的接口

#### 具体工厂（枚举单例）
- **WindowsUIFactory (enum)**: 创建 Windows 风格组件
- **MacUIFactory (enum)**: 创建 Mac 风格组件

### 使用示例

```java
// 创建 Windows 风格 UI
UIFactory factory = WindowsUIFactory.INSTANCE;
Button button = factory.createButton();
TextField textField = factory.createTextField();
button.render();
textField.render();

// 切换到 Mac 风格只需切换工厂
factory = MacUIFactory.INSTANCE;
button = factory.createButton();
textField = factory.createTextField();
button.render();
textField.render();

// 统一处理（多态）
private static void renderUI(UIFactory factory) {
    Button button = factory.createButton();
    TextField textField = factory.createTextField();
    button.render();
    textField.render();
}
```

### 运行示例

```bash
cd 02-factory
mvn clean compile exec:java -Dexec.mainClass="com.designpatterns.abstractfactory.AbstractFactoryDemo"
```

### 输出示例

```
=== 抽象工厂模式演示 ===

--- Windows 风格 ---
渲染 Windows 风格按钮
渲染 Windows 风格文本框

--- Mac 风格 ---
渲染 Mac 风格按钮
渲染 Mac 风格文本框

--- 优点演示 ---
✓ 保证产品族的一致性（同一工厂创建的组件风格统一）
✓ 易于切换产品族（只需切换工厂实例）
✓ 符合开闭原则（新增产品族不修改现有代码）
```

### 优点
✅ **产品族一致性**：保证创建的对象属于同一产品族  
✅ **易于切换**：切换产品族只需切换工厂实例  
✅ **符合 OCP**：新增产品族不修改现有代码  
✅ **隔离具体类**：客户端只依赖抽象接口  

### 适用场景
- 系统需要独立于产品的创建、组合和表示
- 系统需要多个产品族中的一个来配置
- 需要强调一系列相关产品对象的设计以便联合使用
- 提供一个产品类库，只想显示接口而不是实现

### 与工厂方法对比

| 特性 | 工厂方法 | 抽象工厂 |
|------|---------|---------|
| 目的 | 创建**一种**产品 | 创建**一族**相关产品 |
| 工厂接口 | 一个创建方法 | 多个创建方法 |
| 产品关系 | 产品独立 | 产品相关联 |
| 使用场景 | 产品种类扩展 | 产品族切换 |
| 示例 | ShapeFactory | UIFactory (Button + TextField) |

---

## 三种模式总结

| 模式 | 工厂数量 | 产品数量 | 适用场景 | 优势 |
|------|---------|---------|---------|------|
| **简单工厂** | 1 个 | 多个独立产品 | 产品简单、创建逻辑统一 | 最简单 |
| **工厂方法** | 每产品 1 个 | 多个独立产品 | 产品创建逻辑不同 | 符合 OCP |
| **抽象工厂** | 每族 1 个 | 每族多个相关产品 | 需要创建产品族 | 保证一致性 |

---

## 运行所有示例

```bash
cd 02-factory

# 工厂方法 + 简单工厂
mvn exec:java -Dexec.mainClass="com.designpatterns.factory.Main"

# 抽象工厂
mvn exec:java -Dexec.mainClass="com.designpatterns.abstractfactory.AbstractFactoryDemo"
```
