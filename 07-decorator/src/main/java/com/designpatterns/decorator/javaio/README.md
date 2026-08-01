# Java IO 中的装饰器模式

## 概述
Java IO 是装饰器模式的经典应用案例。通过组合不同的流装饰器，可以灵活地为基础流添加各种功能。

## Java IO 装饰器结构

### InputStream 体系
```
InputStream (Component 抽象组件)
├── FileInputStream (ConcreteComponent 具体组件)
├── ByteArrayInputStream (ConcreteComponent)
└── FilterInputStream (Decorator 装饰器基类)
    ├── BufferedInputStream (ConcreteDecorator - 添加缓冲)
    ├── DataInputStream (ConcreteDecorator - 读取基本类型)
    ├── PushbackInputStream (ConcreteDecorator - 推回功能)
    └── UpperCaseInputStream (自定义装饰器 - 转大写)
```

### OutputStream 体系
```
OutputStream (Component 抽象组件)
├── FileOutputStream (ConcreteComponent 具体组件)
├── ByteArrayOutputStream (ConcreteComponent)
└── FilterOutputStream (Decorator 装饰器基类)
    ├── BufferedOutputStream (ConcreteDecorator - 添加缓冲)
    ├── DataOutputStream (ConcreteDecorator - 写入基本类型)
    └── PrintStream (ConcreteDecorator - 格式化输出)
```

## 常见装饰器功能

### 1. BufferedInputStream / BufferedOutputStream
- **功能**: 添加缓冲区，减少实际IO操作次数
- **使用场景**: 提高读写效率

### 2. DataInputStream / DataOutputStream
- **功能**: 读写Java基本数据类型（int, double, boolean等）
- **使用场景**: 需要读写结构化数据

### 3. PrintStream
- **功能**: 提供便捷的格式化输出方法
- **使用场景**: System.out 就是 PrintStream 实例

### 4. ObjectInputStream / ObjectOutputStream
- **功能**: 对象序列化和反序列化
- **使用场景**: 对象持久化、网络传输

## 装饰器组合示例

```java
// 单个装饰器
InputStream in = new BufferedInputStream(new FileInputStream("file.txt"));

// 多重装饰
DataInputStream dis = new DataInputStream(
    new BufferedInputStream(
        new FileInputStream("data.bin")
    )
);

// 自定义装饰器
InputStream customIn = new UpperCaseInputStream(
    new BufferedInputStream(
        new FileInputStream("text.txt")
    )
);
```

## 优势
1. **灵活性**: 可以任意组合不同的装饰器
2. **可扩展性**: 容易添加新的装饰器类型
3. **单一职责**: 每个装饰器只负责一个特定功能
4. **动态组合**: 运行时动态添加或删除功能

## 注意事项
- 装饰器的顺序可能会影响结果
- 使用完毕后要正确关闭流（关闭最外层装饰器即可）
- 过多的装饰器层次可能影响性能和可读性
