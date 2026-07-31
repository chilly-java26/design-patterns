# Spring Boot + 工厂模式 + 配置驱动

## 简介
使用 `@ConstructorProperties` + 反射 + YAML 配置实现**零 switch、零硬编码、参数顺序无关**的工厂模式。

## 核心特性

✅ **零 switch**：所有创建逻辑由反射 + 注解驱动  
✅ **参数顺序无关**：YAML 中 `width` 和 `height` 可以任意顺序  
✅ **新增图形零代码改动**：只新增产品类 + 改 YAML  
✅ **完全符合 OCP**：对扩展开放（新增类），对修改关闭（老代码不动）  
✅ **类型安全**：构造器参数名与 YAML 字段名严格匹配  

## 项目结构

```
src/main/java/com/example/shape/
├── Shape.java                     // 产品接口
├── impl/
│   ├── Rectangle.java             // 矩形
│   ├── Circle.java                // 圆形
│   └── Triangle.java              // 三角形
├── config/
│   └── ShapeConfig.java           // 配置类
├── factory/
│   └── ShapeFactory.java          // 工厂类
└── client/
    └── Application.java           // 启动类

src/main/resources/
└── application.yml                // 配置文件
```

## 运行方式

```bash
cd 03-springboot-drawshape
mvn clean spring-boot:run
```

## 输出示例

```
========== 开始绘制所有图形 ==========
绘制矩形：宽 20.0，高 10.0，面积 200.0
绘制圆形：半径 5.0，面积 78.53981633974483
绘制三角形：底 6.0，高 4.0，面积 12.0
========== 绘制完成 ==========
```

## 新增图形

假设要新增五边形：

### 1. 新增产品类

```java
package com.example.shape.impl;

import com.example.shape.Shape;
import java.beans.ConstructorProperties;

public class Pentagon implements Shape {
    private final double side;

    @ConstructorProperties({"side"})
    public Pentagon(double side) {
        this.side = side;
    }

    @Override
    public void draw() {
        System.out.println("绘制五边形：边长 " + side + "，面积 " + (1.72048 * side * side));
    }
}
```

### 2. 修改 YAML 配置

```yaml
shape:
  shapes:
    - clazz: com.example.shape.impl.Rectangle
      params:
        height: 10.0
        width: 20.0
    - clazz: com.example.shape.impl.Circle
      params:
        radius: 5.0
    - clazz: com.example.shape.impl.Triangle
      params:
        height: 4.0
        base: 6.0
    # ★ 新增五边形，只加这段
    - clazz: com.example.shape.impl.Pentagon
      params:
        side: 3.0
```

**完成！** 无需修改任何已有代码。

## 核心原理

### @ConstructorProperties 注解

```java
@ConstructorProperties({"width", "height"})
public Rectangle(double width, double height) {
    this.width = width;
    this.height = height;
}
```

- 标记构造器参数的名称
- 工厂通过反射读取这些名称
- 从 YAML 配置中按名称匹配参数值
- **参数顺序无关**：工厂会按构造器顺序排列参数

## 优势对比

| 特性 | 传统 switch 工厂 | 本方案 |
|------|-----------------|--------|
| 新增图形 | 需修改工厂类 | 只加类 + 改配置 |
| 参数顺序 | 硬编码顺序 | 完全无关 |
| 类型安全 | 易出错 | 编译期检查 |
| 扩展性 | 违反 OCP | 完全符合 OCP |

## 技术栈

- Spring Boot 2.7.18
- Java 8
- Maven
- YAML 配置
