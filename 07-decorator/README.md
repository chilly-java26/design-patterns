# 装饰器模式（Decorator Pattern）

## 概述
装饰器模式允许向一个现有的对象添加新的功能，同时又不改变其结构。这种模式创建了一个装饰类，用来包装原有的类，并在保持类方法签名完整性的前提下，提供了额外的功能。

## 核心组件

### 1. Component（组件接口）
- `Coffee` - 定义咖啡的基本接口

### 2. ConcreteComponent（具体组件）
- `SimpleCoffee` - 基础的咖啡实现

### 3. Decorator（装饰器抽象类）
- `CoffeeDecorator` - 装饰器的抽象基类

### 4. ConcreteDecorator（具体装饰器）
- `MilkDecorator` - 添加牛奶的装饰器
- `SugarDecorator` - 添加糖的装饰器

## 本质
可多层叠加：
- 调用叠加：装饰器（构造函数返回值）和被装饰对象（构造函数参数）实现了同一个接口 Coffee
- 功能叠加：持有被装饰对象 Coffee 的引用，调用它的方法可进行功能叠加

## 使用场景
- 在不影响其他对象的情况下，以动态、透明的方式给单个对象添加职责
- 需要动态地给一个对象增加功能，这些功能可以再动态地撤销
- 当不能采用继承的方式对系统进行扩展或者采用继承不利于系统扩展和维护时

## 优点
- 装饰类和被装饰类可以独立发展，不会相互耦合
- 装饰模式是继承的一个替代模式，可以动态扩展一个实现类的功能
- 可以对一个对象进行多次装饰，通过使用不同的装饰类以及这些装饰类的排列组合，可以创造出很多不同行为的组合

## 运行示例

### 运行咖啡装饰示例
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.designpatterns.decorator.DecoratorDemo"
```

### 运行Java IO装饰示例
```bash
mvn exec:java -Dexec.mainClass="com.designpatterns.decorator.javaio.JavaIODecoratorDemo"
```

### 运行MyBatis缓存装饰示例
```bash
mvn exec:java -Dexec.mainClass="com.designpatterns.decorator.mybatis.MyBatisCacheDemo"
```

## 示例说明

### 1. 咖啡装饰示例 (decorator包)
使用咖啡为例演示基础装饰器模式：
- 基础咖啡 `SimpleCoffee`
- 可以添加牛奶装饰 `MilkDecorator`
- 可以添加糖装饰 `SugarDecorator`
- 可以组合多个装饰器，如：加奶加糖的咖啡

### 2. Java IO 装饰示例 (javaio包)
展示Java IO中装饰器模式的实际应用：
- `BufferedInputStream` - 添加缓冲功能
- `DataInputStream` - 添加读取基本类型功能
- `PrintStream` - 添加格式化输出功能
- `UpperCaseInputStream` - 自定义装饰器示例

Java IO是装饰器模式在JDK中的经典应用，通过组合不同的流装饰器可以灵活地添加各种功能。

### 3. MyBatis 缓存装饰示例 (mybatis包)
模拟MyBatis中缓存系统的装饰器实现：
- `PerpetualCache` - 基础缓存（使用HashMap）
- `LruCache` - LRU淘汰策略装饰器
- `LoggingCache` - 日志统计装饰器
- `SynchronizedCache` - 线程安全装饰器

MyBatis通过装饰器模式实现了灵活的缓存增强系统，可以根据配置动态组合不同的缓存功能。
