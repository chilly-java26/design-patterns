# 迭代器模式实现教程

## 一、核心概念

迭代器模式的本质：**将遍历逻辑从集合对象中分离出来，提供统一的访问接口**

## 二、实现步骤

### 步骤 1：定义迭代器接口

```java
public interface Iterator<T> {
    boolean hasNext();  // 是否还有下一个元素
    T next();           // 获取下一个元素
}
```

**关键点：**
- 使用泛型支持不同类型的元素
- 只定义最基本的遍历操作

### 步骤 2：定义聚合对象接口

```java
public interface Aggregate<T> {
    Iterator<T> iterator();  // 创建迭代器
}
```

**关键点：**
- 聚合对象负责创建自己的迭代器
- 返回的迭代器知道如何遍历该聚合对象

### 步骤 3：创建数据类

```java
public class Book {
    private String title;
    private String author;
    
    // 构造函数、getter 和 toString
}
```

**关键点：**
- 简单的 POJO 类
- 存储需要被迭代的数据

### 步骤 4：实现具体聚合对象

```java
public class BookShelf implements Aggregate<Book> {
    private List<Book> books;
    
    public void addBook(Book book) { ... }
    public int getLength() { ... }
    public Book getBookAt(int index) { ... }
    
    @Override
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }
}
```

**关键点：**
- 内部使用 List 存储，但这是实现细节
- 提供必要的访问方法供迭代器使用
- `iterator()` 方法创建并返回迭代器实例

### 步骤 5：实现具体迭代器

```java
public class BookShelfIterator implements Iterator<Book> {
    private BookShelf bookShelf;
    private int index;
    
    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }
    
    @Override
    public boolean hasNext() {
        return index < bookShelf.getLength();
    }
    
    @Override
    public Book next() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("没有更多的书籍了");
        }
        Book book = bookShelf.getBookAt(index);
        index++;
        return book;
    }
}
```

**关键点：**
- 持有聚合对象的引用
- 维护当前遍历位置（index）
- `hasNext()` 检查是否到达末尾
- `next()` 返回当前元素并移动指针

### 步骤 6：客户端使用

```java
// 创建书架并添加书籍
BookShelf bookShelf = new BookShelf();
bookShelf.addBook(new Book("设计模式", "GoF"));
bookShelf.addBook(new Book("重构", "Martin Fowler"));

// 获取迭代器并遍历
Iterator<Book> iterator = bookShelf.iterator();
while (iterator.hasNext()) {
    Book book = iterator.next();
    System.out.println(book);
}
```

**关键点：**
- 客户端不需要知道书架的内部结构
- 使用统一的 hasNext() + next() 模式
- 循环逻辑简洁清晰

## 三、设计要点

### 1. 封装性

```
客户端 ──► Iterator 接口 ──► 遍历逻辑
           ↑
           │
    BookShelfIterator（实现细节隐藏）
```

客户端只依赖接口，不依赖具体实现。

### 2. 职责分离

| 类 | 职责 |
|---|---|
| BookShelf | 存储和管理书籍 |
| BookShelfIterator | 遍历书籍 |
| 客户端 | 使用统一接口遍历 |

### 3. 灵活性

如果将 BookShelf 的内部实现从 List 改为数组：

```java
// 改变内部实现
private Book[] books;
private int size;

// 只需修改 BookShelfIterator 的实现
// 客户端代码完全不受影响
```

## 四、对比 Java 标准库

### 我们的实现 vs Java Iterator

```java
// 我们的实现
Iterator<Book> iterator = bookShelf.iterator();
while (iterator.hasNext()) {
    Book book = iterator.next();
}

// Java 标准库
java.util.Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String item = iterator.next();
}
```

**几乎一模一样！** 这就是设计模式的力量。

### Java 增强 for 循环

Java 的增强 for 循环底层就是使用迭代器：

```java
// 这段代码
for (Book book : bookShelf) {
    System.out.println(book);
}

// 编译后等价于
Iterator<Book> iterator = bookShelf.iterator();
while (iterator.hasNext()) {
    Book book = iterator.next();
    System.out.println(book);
}
```

要支持增强 for 循环，聚合类需要实现 `Iterable` 接口：

```java
public class BookShelf implements Iterable<Book> {
    @Override
    public java.util.Iterator<Book> iterator() {
        return books.iterator();
    }
}
```

## 五、扩展示例

### 1. 支持反向遍历

```java
public interface BidirectionalIterator<T> extends Iterator<T> {
    boolean hasPrevious();
    T previous();
}
```

### 2. 支持删除操作

```java
public interface Iterator<T> {
    boolean hasNext();
    T next();
    void remove();  // 删除当前元素
}
```

### 3. 多种遍历策略

```java
public class BookShelf {
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }
    
    public Iterator<Book> reverseIterator() {
        return new ReverseBookShelfIterator(this);
    }
    
    public Iterator<Book> randomIterator() {
        return new RandomBookShelfIterator(this);
    }
}
```

## 六、实战应用场景

### 1. 文件系统遍历

```java
FileSystemIterator iterator = folder.iterator();
while (iterator.hasNext()) {
    File file = iterator.next();
    process(file);
}
```

### 2. 数据库结果集

```java
ResultSet rs = statement.executeQuery("SELECT * FROM books");
while (rs.next()) {  // next() 类似迭代器
    String title = rs.getString("title");
}
```

### 3. 树形结构遍历

```java
// 前序遍历迭代器
Iterator<TreeNode> preOrderIterator = tree.preOrderIterator();

// 中序遍历迭代器
Iterator<TreeNode> inOrderIterator = tree.inOrderIterator();

// 后序遍历迭代器
Iterator<TreeNode> postOrderIterator = tree.postOrderIterator();
```

## 七、常见问题

### Q1: 迭代器和 for 循环有什么区别？

**A:** for 循环暴露了集合的内部结构（需要知道是数组还是 List），而迭代器提供了统一的访问接口。

```java
// 需要知道内部是数组
for (int i = 0; i < books.length; i++) {
    Book book = books[i];
}

// 不需要知道内部结构
Iterator<Book> iterator = bookShelf.iterator();
while (iterator.hasNext()) {
    Book book = iterator.next();
}
```

### Q2: 为什么需要 Aggregate 接口？

**A:** 为了让所有聚合对象都能以统一的方式创建迭代器。

```java
public void printAll(Aggregate<?> aggregate) {
    Iterator<?> iterator = aggregate.iterator();
    while (iterator.hasNext()) {
        System.out.println(iterator.next());
    }
}
```

### Q3: 可以同时有多个迭代器吗？

**A:** 可以！每次调用 `iterator()` 都会创建新的迭代器实例。

```java
Iterator<Book> it1 = bookShelf.iterator();
Iterator<Book> it2 = bookShelf.iterator();

// it1 和 it2 独立工作，互不影响
```

## 八、最佳实践

1. **迭代器应该是轻量级的**
   - 只存储必要的状态（如当前位置）
   - 不要复制整个集合

2. **及时检查边界条件**
   - 在 `next()` 中检查 `hasNext()`
   - 防止越界访问

3. **保持不变性**
   - 迭代过程中不要修改集合
   - 或者实现 fail-fast 机制（Java 的做法）

4. **遵循单一职责**
   - 迭代器只负责遍历
   - 不要在迭代器中添加业务逻辑

## 九、总结

迭代器模式的核心价值：

1. ✅ **封装内部结构** - 客户端不需要知道集合如何存储
2. ✅ **统一访问接口** - 所有集合都用相同方式遍历
3. ✅ **职责分离** - 遍历逻辑独立于存储逻辑
4. ✅ **灵活扩展** - 可以轻松添加新的遍历方式

**记住：** 迭代器模式不是为了炫技，而是为了让代码更加清晰、灵活和易于维护！
