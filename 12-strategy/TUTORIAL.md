# 策略模式深入教程

## 📚 什么是策略模式？

**策略模式的本质：将算法封装成独立的策略类，让它们可以互相替换，使算法的变化独立于使用算法的客户端。**

简单理解：就像你去旅游，可以选择飞机、火车、汽车等不同的交通方式。每种方式都能到达目的地，但各有特点。你可以根据需求随时切换，而旅游社不需要改变服务流程。

## 🎯 为什么需要策略模式？

### 问题场景

假设你要写一个计算价格的方法：

```java
public double calculatePrice(String userType, double price) {
    if ("NORMAL".equals(userType)) {
        return price;  // 普通用户
    } else if ("VIP".equals(userType)) {
        return price * 0.9;  // VIP 9折
    } else if ("SUPER_VIP".equals(userType)) {
        return price * 0.8;  // 超级VIP 8折
    } else if ("DIAMOND_VIP".equals(userType)) {
        return price * 0.7;  // 钻石VIP 7折
    }
    // ... 如果还有更多用户类型呢？
    return price;
}
```

**问题：**
1. ❌ 每增加一种用户类型，都要修改这个方法
2. ❌ 方法会越来越长，难以维护
3. ❌ 违反了"开闭原则"（对扩展开放，对修改关闭）
4. ❌ 所有逻辑耦合在一起，难以测试

### 策略模式的解决方案

```java
// 1. 定义策略接口
interface DiscountStrategy {
    double calculatePrice(double price);
}

// 2. 实现具体策略
class VIPDiscountStrategy implements DiscountStrategy {
    public double calculatePrice(double price) {
        return price * 0.9;
    }
}

// 3. 使用策略
DiscountStrategy strategy = new VIPDiscountStrategy();
double finalPrice = strategy.calculatePrice(1000);
```

**优势：**
1. ✅ 新增用户类型只需新增策略类
2. ✅ 每个策略独立，易于维护
3. ✅ 符合"开闭原则"
4. ✅ 每个策略可以独立测试

## 🏗️ 策略模式的三个角色

### 1. Strategy（策略接口）

定义所有策略的统一接口。

```java
public interface PaymentStrategy {
    void pay(double amount);
}
```

### 2. ConcreteStrategy（具体策略）

实现策略接口，提供具体算法。

```java
public class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("信用卡支付: " + amount);
    }
}

public class AlipayPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("支付宝支付: " + amount);
    }
}
```

### 3. Context（上下文）

持有策略对象的引用，可以动态切换策略。

```java
public class ShoppingCart {
    private PaymentStrategy strategy;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void checkout(double amount) {
        strategy.pay(amount);
    }
}
```

## 🎬 完整示例演示

### 示例1：支付方式选择

运行 `Main.java` 查看效果：

```bash
mvn exec:java -Dexec.mainClass="com.designpatterns.strategy.Main"
```

**代码逻辑：**
- 购物车可以切换不同的支付方式
- 支付逻辑封装在各自的策略类中
- 添加新支付方式不需要修改购物车代码

### 示例2：消除if-else

运行 `DiscountDemo.java` 查看对比：

```bash
mvn exec:java -Dexec.mainClass="com.designpatterns.strategy.DiscountDemo"
```

**对比效果：**
- 不使用策略模式：大量if-else判断
- 使用策略模式：清爽的循环调用

## 🔥 实战应用场景

### 1. 电商系统

```java
// 不同促销活动的价格计算
interface PromotionStrategy {
    double calculate(double price);
}

class DoubleElevenStrategy implements PromotionStrategy { ... }
class BlackFridayStrategy implements PromotionStrategy { ... }
class ChristmasStrategy implements PromotionStrategy { ... }
```

### 2. 数据导出

```java
// 不同格式的数据导出
interface ExportStrategy {
    void export(List<Data> data);
}

class ExcelExportStrategy implements ExportStrategy { ... }
class PDFExportStrategy implements ExportStrategy { ... }
class CSVExportStrategy implements ExportStrategy { ... }
```

### 3. 排序算法

```java
// 不同的排序策略
interface SortStrategy {
    void sort(int[] array);
}

class QuickSortStrategy implements SortStrategy { ... }
class BubbleSortStrategy implements SortStrategy { ... }
class MergeSortStrategy implements SortStrategy { ... }
```

### 4. 路径规划

```java
// 导航系统的不同路线策略
interface RouteStrategy {
    Route calculate(Point start, Point end);
}

class FastestRouteStrategy implements RouteStrategy { ... }
class ShortestRouteStrategy implements RouteStrategy { ... }
class CheapestRouteStrategy implements RouteStrategy { ... }
```

## 💡 进阶技巧

### 技巧1：结合工厂模式

```java
public class StrategyFactory {
    private static Map<String, DiscountStrategy> strategies = new HashMap<>();
    
    static {
        strategies.put("NORMAL", new NoDiscountStrategy());
        strategies.put("VIP", new VIPDiscountStrategy());
        strategies.put("SUPER_VIP", new SuperVIPDiscountStrategy());
    }
    
    public static DiscountStrategy getStrategy(String userType) {
        return strategies.get(userType);
    }
}
```

### 技巧2：使用枚举实现策略

```java
public enum DiscountStrategy {
    NORMAL {
        public double calculate(double price) { return price; }
    },
    VIP {
        public double calculate(double price) { return price * 0.9; }
    },
    SUPER_VIP {
        public double calculate(double price) { return price * 0.8; }
    };
    
    public abstract double calculate(double price);
}
```

### 技巧3：Lambda表达式（Java 8+）

```java
// 策略接口只有一个方法时，可以使用Lambda
@FunctionalInterface
interface PriceCalculator {
    double calculate(double price);
}

// 使用
PriceCalculator vip = price -> price * 0.9;
PriceCalculator superVip = price -> price * 0.8;
```

## ⚠️ 注意事项

### 1. 策略对象的创建时机

**问题：** 是否每次都要 `new` 一个策略对象？

**方案：**
- 无状态策略：可以作为单例复用
- 有状态策略：每次创建新对象

```java
// 单例策略（无状态）
public class VIPDiscountStrategy implements DiscountStrategy {
    private static final VIPDiscountStrategy INSTANCE = new VIPDiscountStrategy();
    
    private VIPDiscountStrategy() {}
    
    public static VIPDiscountStrategy getInstance() {
        return INSTANCE;
    }
}
```

### 2. 策略的选择

**问题：** 客户端如何知道选择哪个策略？

**方案：**
- 方案1：客户端根据业务逻辑选择
- 方案2：通过工厂模式封装选择逻辑
- 方案3：通过配置文件或注解

### 3. 策略数量过多

**问题：** 如果有100种策略怎么办？

**方案：**
- 考虑是否可以合并相似策略
- 使用策略组合模式
- 考虑使用配置文件+反射动态加载

## 🆚 与其他模式对比

### 策略模式 vs 状态模式

| 特点 | 策略模式 | 状态模式 |
|------|---------|---------|
| 目的 | 让算法可以互相替换 | 让对象在不同状态下有不同行为 |
| 切换者 | 客户端主动切换 | 状态内部自动切换 |
| 独立性 | 策略之间相互独立 | 状态之间有依赖关系 |
| 例子 | 支付方式选择 | 订单状态流转 |

### 策略模式 vs 工厂模式

| 特点 | 策略模式 | 工厂模式 |
|------|---------|---------|
| 关注点 | 算法的替换和执行 | 对象的创建 |
| 使用时机 | 需要动态切换算法 | 需要创建不同对象 |
| 可以结合 | ✅ 常常一起使用 | 工厂创建策略对象 |

## 🎓 学习建议

1. **理解本质**：策略模式的核心是"算法封装"和"互相替换"
2. **从实际出发**：遇到多个if-else时，考虑是否可以用策略模式
3. **循序渐进**：先掌握基本用法，再学习进阶技巧
4. **动手实践**：自己实现一个完整的策略模式示例
5. **举一反三**：思考工作中哪些场景可以应用策略模式

## 📝 练习题

1. 实现一个文件压缩系统，支持ZIP、RAR、7Z三种压缩格式
2. 实现一个计算器，支持加、减、乘、除四种运算策略
3. 实现一个物流系统，支持顺丰、圆通、中通等不同配送策略
4. 结合工厂模式，优化本项目的支付策略选择逻辑

## 📖 延伸阅读

- 《设计模式：可复用面向对象软件的基础》- GoF
- 《Head First 设计模式》
- Spring框架中的策略模式应用（如：Resource资源访问策略）
