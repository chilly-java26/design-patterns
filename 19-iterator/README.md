# 迭代器模式（Iterator Pattern）

## 定义

**迭代器模式**提供一种方法顺序访问聚合对象中的元素，而不暴露其内部表示。

## 核心思想

将遍历逻辑从集合对象中分离出来，封装到独立的迭代器对象中，使得可以用统一的方式遍历不同类型的集合。

## 角色组成

1. **Iterator（迭代器接口）**
   - 定义访问和遍历元素的接口
   - `hasNext()`: 判断是否还有下一个元素
   - `next()`: 获取下一个元素

2. **ConcreteIterator（具体迭代器）**
   - 实现迭代器接口
   - 跟踪遍历时的当前位置
   - 示例：`BookShelfIterator`

3. **Aggregate（聚合接口）**
   - 定义创建迭代器的接口
   - `iterator()`: 返回迭代器对象

4. **ConcreteAggregate（具体聚合）**
   - 实现创建迭代器的接口
   - 管理对象集合
   - 示例：`BookShelf`

## 类图关系

```
┌─────────────────┐         ┌──────────────────┐
│   <<interface>> │         │  <<interface>>   │
│    Iterator     │         │    Aggregate     │
├─────────────────┤         ├──────────────────┤
│ + hasNext()     │         │ + iterator()     │
│ + next()        │         │                  │
└────────┬────────┘         └────────┬─────────┘
         △                           △
         │                           │
         │ implements                │ implements
         │                           │
┌────────┴─────────────┐   ┌────────┴──────────┐
│ BookShelfIterator    │   │    BookShelf      │
├──────────────────────┤   ├───────────────────┤
│ - bookShelf          │◄──│ - books: List     │
│ - index              │   │                   │
├──────────────────────┤   ├───────────────────┤
│ + hasNext()          │   │ + addBook()       │
│ + next()             │   │ + getLength()     │
└──────────────────────┘   │ + getBookAt()     │
                           │ + iterator()      │
                           └───────────────────┘
```

## 示例场景

图书馆书架遍历：
- 书架存储多本书籍
- 使用迭代器遍历所有书籍
- 客户端不需要知道书架内部的存储结构

## 运行示例

```bash
cd 19-iterator
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.iterator.IteratorDemo"
```

## 输出示例

```
=== 迭代器模式演示 ===

书架上的所有书籍：

1. 《设计模式：可复用面向对象软件的基础》 - GoF
2. 《重构：改善既有代码的设计》 - Martin Fowler
3. 《代码整洁之道》 - Robert C. Martin
4. 《Effective Java》 - Joshua Bloch
5. 《深入理解Java虚拟机》 - 周志明

=== 演示完成 ===
```

## 优势

1. **封装性好**
   - 隐藏集合的内部结构
   - 客户端通过统一接口访问

2. **单一职责**
   - 遍历逻辑独立于集合对象
   - 集合专注于存储管理

3. **灵活性强**
   - 可以为同一个集合提供多种遍历方式
   - 可以同时存在多个遍历过程

4. **符合开闭原则**
   - 增加新的集合类和迭代器类无需修改现有代码

## 缺点

1. 增加了系统复杂性（需要额外的迭代器类）
2. 对于简单的遍历场景可能有些重量级

## 适用场景

1. 需要访问集合对象的内容而无需暴露其内部表示
2. 需要为聚合对象提供多种遍历方式
3. 需要为遍历不同的聚合结构提供统一的接口

## 实际应用

### Java 集合框架

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");

// 使用迭代器遍历
java.util.Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String element = iterator.next();
    System.out.println(element);
}

// 增强 for 循环底层也是使用迭代器
for (String element : list) {
    System.out.println(element);
}
```

### 数据库结果集

```java
ResultSet rs = statement.executeQuery("SELECT * FROM users");
while (rs.next()) { // next() 方法类似迭代器
    String name = rs.getString("name");
    System.out.println(name);
}
```

## 扩展

### 支持双向遍历

可以扩展迭代器接口，增加 `previous()` 和 `hasPrevious()` 方法，实现双向遍历。

### 支持删除操作

Java 的 `Iterator` 接口提供了 `remove()` 方法，可以在遍历过程中安全地删除元素。

## 与其他模式的关系

- **组合模式**：常与迭代器一起使用，遍历组合结构
- **工厂模式**：可以使用工厂模式创建不同类型的迭代器
- **备忘录模式**：可以配合使用，保存迭代器的状态

## 总结

迭代器模式将遍历逻辑从集合中分离出来，提供了一种统一、简洁的方式来访问集合元素，是面向对象设计中非常基础和重要的模式之一。Java 集合框架的成功很大程度上得益于迭代器模式的良好应用。
