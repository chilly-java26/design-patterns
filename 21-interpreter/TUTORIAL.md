# 解释器模式教程

## 什么是解释器模式？

解释器模式是一种将**语法规则对象化**的设计模式。它的核心思想是：
- 把运算符（+、-、*、/）变成对象
- 把表达式变成对象树
- 通过递归调用对象的方法来计算结果

## 一句话总结

**用对象表示文法规则，递归解释执行表达式。**

## 为什么需要解释器模式？

假设你要计算表达式 `10 + 5 - 3`：

### 传统方式
```java
int result = 10 + 5 - 3; // 直接计算
```

但如果表达式是用户输入的字符串呢？
```java
String expr = "10 + 5 - 3"; // 如何计算？
```

### 解释器模式的方式
```java
// 1. 把字符串解析成对象树
Expression tree = parser.parse("10 + 5 - 3");

// 2. 对象树自动递归计算
int result = tree.interpret(); // 12
```

## 核心概念

### 1. 表达式接口
所有表达式都实现这个接口：
```java
interface Expression {
    int interpret(); // 解释并返回结果
}
```

### 2. 终结符表达式（叶子节点）
表示不能再分解的基本元素，比如数字：
```java
class NumberExpression implements Expression {
    private int number;
    
    public int interpret() {
        return number; // 直接返回数字
    }
}
```

### 3. 非终结符表达式（组合节点）
表示可以继续分解的复杂表达式，比如加法：
```java
class AddExpression implements Expression {
    private Expression left;  // 左操作数
    private Expression right; // 右操作数
    
    public int interpret() {
        // 递归计算左右两边，然后相加
        return left.interpret() + right.interpret();
    }
}
```

## 工作原理

以 `10 + 5 - 3` 为例：

### 步骤1：构建对象树
```
       SubtractExpression
          /           \
   AddExpression       3
      /       \
     10        5
```

### 步骤2：递归计算
```java
// 调用根节点的 interpret()
result = subtract.interpret()
       = add.interpret() - 3
       = (10 + 5) - 3
       = 15 - 3
       = 12
```

## 代码实现详解

### 完整示例：10 + 5 - 3

```java
// 1. 创建数字节点
Expression num10 = new NumberExpression(10);
Expression num5 = new NumberExpression(5);
Expression num3 = new NumberExpression(3);

// 2. 创建加法节点
Expression add = new AddExpression(num10, num5);

// 3. 创建减法节点（根节点）
Expression subtract = new SubtractExpression(add, num3);

// 4. 计算结果
int result = subtract.interpret(); // 12
```

### 扩展：添加乘法和除法

只需要添加新的类，不需要修改现有代码：

```java
class MultiplyExpression implements Expression {
    private Expression left, right;
    
    public int interpret() {
        return left.interpret() * right.interpret();
    }
}

class DivideExpression implements Expression {
    private Expression left, right;
    
    public int interpret() {
        return left.interpret() / right.interpret();
    }
}
```

## 进阶：解析器

手动构建表达式树太麻烦，我们需要一个解析器：

```java
ExpressionParser parser = new ExpressionParser();

// 从字符串直接生成表达式树
Expression expr = parser.parse("20 * 3 + 8 / 2");

// 计算结果
System.out.println(expr.interpret()); // 64
```

解析器的工作：
1. 分词：`"20 * 3 + 8 / 2"` → `["20", "*", "3", "+", "8", "/", "2"]`
2. 处理优先级：乘除法优先于加减法
3. 构建表达式树
4. 返回根节点

## 优点和缺点

### 优点
✅ **易于扩展** - 添加新运算符只需新增一个类  
✅ **灵活组合** - 可以任意组合表达式  
✅ **符合开闭原则** - 对扩展开放，对修改关闭  
✅ **表达式可复用** - 可以序列化、缓存、传递

### 缺点
❌ **类数量多** - 每个规则都需要一个类  
❌ **维护困难** - 复杂文法会导致类爆炸  
❌ **性能较低** - 递归调用和对象创建有开销  
❌ **适用范围窄** - 只适合简单语法

## 何时使用？

适合：
- ✅ 简单的语法规则
- ✅ 需要频繁扩展新规则
- ✅ 性能不是关键因素
- ✅ 需要运行时动态组合表达式

不适合：
- ❌ 复杂的编程语言解析（用 ANTLR 等工具）
- ❌ 性能要求高的场景
- ❌ 文法频繁变化的场景

## 实际应用场景

1. **数学表达式计算器**
   - 计算器应用
   - Excel 公式

2. **正则表达式引擎**
   - 字符串匹配
   - 模式搜索

3. **SQL 查询解析**
   - WHERE 条件解析
   - SELECT 语句解析

4. **配置文件解析**
   - 自定义 DSL
   - 规则引擎

5. **编译器前端**
   - 词法分析
   - 语法分析

## 与其他模式的关系

- **组合模式**：解释器模式使用组合模式构建表达式树
- **访问者模式**：可以用访问者模式遍历表达式树
- **享元模式**：可以共享相同的终结符对象
- **迭代器模式**：可以用迭代器遍历表达式树

## 总结

解释器模式的本质是：
> **将语法规则映射为对象结构，通过对象组合来解析和执行特定语言的句子。**

简单来说：
> **把运算符变成对象，用递归来计算。**

记住这句话，你就理解了解释器模式的核心思想！
