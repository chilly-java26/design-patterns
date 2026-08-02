# 解释器模式 (Interpreter Pattern)

## 概述

解释器模式是一种行为型设计模式，用于定义语言的文法，并建立一个解释器来解释该语言中的句子。

## 核心思想

**将语法规则映射为对象结构，通过对象组合来解析和执行特定语言的句子。**

简单来说：**用对象表示文法规则，递归解释执行表达式。**

## 模式结构

### 角色组成

1. **抽象表达式 (Abstract Expression)**
   - 定义解释操作的接口
   - `Expression` 接口

2. **终结符表达式 (Terminal Expression)**
   - 实现文法中终结符相关的解释操作
   - `NumberExpression` - 表示数字

3. **非终结符表达式 (Nonterminal Expression)**
   - 实现文法中非终结符相关的解释操作
   - `AddExpression` - 加法运算
   - `SubtractExpression` - 减法运算
   - `MultiplyExpression` - 乘法运算
   - `DivideExpression` - 除法运算

4. **上下文/解析器 (Context/Parser)**
   - 包含解释器之外的全局信息
   - `ExpressionParser` - 解析字符串为表达式树

## 示例说明

本示例实现了一个简单的数学表达式解释器，支持加减乘除运算。

### 核心实现

```java
// 抽象表达式
interface Expression {
    int interpret();
}

// 终结符表达式 - 数字
class NumberExpression implements Expression {
    private int number;
    
    public int interpret() {
        return number;
    }
}

// 非终结符表达式 - 加法
class AddExpression implements Expression {
    private Expression left, right;
    
    public int interpret() {
        return left.interpret() + right.interpret();
    }
}
```

### 使用方式

```java
// 方式1：手动构建表达式树
Expression expr = new SubtractExpression(
    new AddExpression(
        new NumberExpression(10),
        new NumberExpression(5)
    ),
    new NumberExpression(3)
);
System.out.println(expr.interpret()); // 输出: 12

// 方式2：使用解析器
ExpressionParser parser = new ExpressionParser();
Expression expr2 = parser.parse("20 * 3 + 8 / 2");
System.out.println(expr2.interpret()); // 输出: 64
```

## 优缺点

### 优点

1. **易于扩展** - 添加新的解释规则只需新增类
2. **灵活组合** - 可以灵活组合不同的表达式
3. **符合开闭原则** - 对扩展开放，对修改关闭
4. **表达式可复用** - 对象可以被序列化、持久化、传递

### 缺点

1. **类数量增加** - 每个规则都需要一个类
2. **维护困难** - 复杂文法会导致类层次结构复杂
3. **执行效率低** - 递归调用和对象创建影响性能
4. **适用场景有限** - 只适合简单的语法规则

## 适用场景

1. **简单语法** - 文法规则简单且稳定
2. **执行效率不是关键** - 可以接受递归调用的开销
3. **需要可扩展性** - 经常需要添加新的语法规则
4. **领域特定语言 (DSL)** - 自定义配置语言、查询语言等

## 实际应用

- **正则表达式引擎** - 解释正则表达式
- **SQL 解析器** - 解析 SQL 语句
- **数学表达式计算器** - 计算数学表达式
- **配置文件解析** - 解析特定格式的配置
- **编译器前端** - 词法分析和语法分析

## 运行示例

### 数学表达式解释器
```bash
cd 21-interpreter
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.interpreter.InterpreterDemo"
```

### SQL WHERE 条件解释器
```bash
cd 21-interpreter
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.interpreter.sql.SqlWhereInterpreterDemo"
```

## 与其他模式的关系

- **组合模式** - 解释器模式通常使用组合模式来组织表达式树
- **迭代器模式** - 可以用迭代器遍历表达式树
- **访问者模式** - 可以用访问者模式在表达式树上执行操作
- **享元模式** - 可以共享终结符表达式对象

## 总结

解释器模式将语法规则对象化，通过对象组合和递归调用实现表达式的解释执行。适合简单且稳定的文法规则，但不适合复杂的语法解析场景。

本模块包含两个示例：
1. **数学表达式解释器** - 演示基本的解释器模式实现
2. **SQL WHERE 条件解释器** - 演示非数学场景的语法解析应用
