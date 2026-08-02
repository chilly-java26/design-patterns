# 为什么不直接 loop books？

## 问题的本质

你说得对，直接遍历 `books` 确实更简单！那为什么还要用迭代器？

## 核心矛盾

```java
// 方式1: 直接暴露 List
public List<Book> getBooks() {
    return books;  // 简单！但有问题...
}

// 使用
for (Book book : shelf.getBooks()) {
    System.out.println(book);
}
```

### 问题1: 破坏封装

```java
BookShelf shelf = new BookShelf();
shelf.addBook(new Book("设计模式", "GoF"));

// 客户端可以直接修改！
shelf.getBooks().clear();  // 清空了！
shelf.getBooks().add(new Book("XXX", "YYY"));  // 绕过了验证逻辑
```

**如果加业务逻辑怎么办？**

```java
class BookShelf {
    public void addBook(Book book) {
        // 业务逻辑：只允许添加价格 > 0 的书
        if (book.getPrice() <= 0) {
            throw new IllegalArgumentException("价格必须大于0");
        }
        books.add(book);
    }
    
    public List<Book> getBooks() {
        return books;  // 客户端可以绕过 addBook 直接添加！
    }
}
```

### 问题2: 内部实现改变时，客户端代码全部要改

**场景：性能优化，List 改为数组**

```java
// 之前：使用 List
class BookShelf {
    private List<Book> books;
    public List<Book> getBooks() { return books; }
}

// 客户端代码
for (Book book : shelf.getBooks()) { ... }  // ✓ 正常工作
```

```java
// 现在：改用数组
class BookShelf {
    private Book[] books;
    private int size;
    public Book[] getBooks() { return books; }  // 返回类型变了
    public int getSize() { return size; }
}

// 客户端代码全部要改！
for (int i = 0; i < shelf.getSize(); i++) {  // ✗ 必须改代码
    Book book = shelf.getBooks()[i];
    ...
}
```

**有多少个客户端使用了 `getBooks()`，就要改多少处！**

### 问题3: 不同遍历方式导致代码重复

```java
// 正向遍历
for (int i = 0; i < shelf.getBooks().size(); i++) {
    Book book = shelf.getBooks().get(i);
    System.out.println(book);
}

// 反向遍历
for (int i = shelf.getBooks().size() - 1; i >= 0; i--) {
    Book book = shelf.getBooks().get(i);
    System.out.println(book);
}

// 每隔一个遍历
for (int i = 0; i < shelf.getBooks().size(); i += 2) {
    Book book = shelf.getBooks().get(i);
    System.out.println(book);
}
```

遍历逻辑分散在各处，难以维护。

## 迭代器模式的解决方案

### 1. 完全封装，无法绕过

```java
class BookShelf {
    private List<Book> books;  // private
    
    // 没有 getBooks() 方法！
    
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }
}

// 客户端只能通过迭代器访问
Iterator<Book> it = shelf.iterator();
while (it.hasNext()) {
    Book book = it.next();  // 无法直接修改集合
}
```

### 2. 内部实现改变，客户端代码不变

```java
// List 实现
class ListBookShelf implements Aggregate<Book> {
    private List<Book> books;
    public Iterator<Book> iterator() { ... }
}

// 数组实现
class ArrayBookShelf implements Aggregate<Book> {
    private Book[] books;
    public Iterator<Book> iterator() { ... }
}

// 客户端代码完全一样！
void printBooks(Aggregate<Book> shelf) {
    Iterator<Book> it = shelf.iterator();
    while (it.hasNext()) {
        System.out.println(it.next());
    }
}
```

### 3. 遍历逻辑集中管理

```java
class BookShelf {
    // 正向迭代器
    public Iterator<Book> iterator() { ... }
    
    // 反向迭代器
    public Iterator<Book> reverseIterator() { ... }
    
    // 随机迭代器
    public Iterator<Book> randomIterator() { ... }
}

// 客户端使用统一接口
Iterator<Book> it = shelf.reverseIterator();
while (it.hasNext()) {
    System.out.println(it.next());
}
```

## 真实世界的例子

### Java 集合框架

Java 为什么不让你直接访问 ArrayList 内部的数组？

```java
List<String> list = new ArrayList<>();

// 你不能这样做：
// String[] array = list.getInternalArray();  // 不存在这个方法

// 只能通过迭代器：
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}
```

原因：
1. 保护内部数组不被破坏
2. 可以轻松替换实现（ArrayList → LinkedList）
3. 统一的遍历接口

### 数据库 ResultSet

```java
ResultSet rs = statement.executeQuery("SELECT * FROM books");
while (rs.next()) {  // 类似迭代器的 next()
    String title = rs.getString("title");
}
```

你能直接访问数据库返回的内部数据结构吗？不能！  
为什么？因为封装！

## 什么时候可以直接 loop？

**如果满足以下所有条件，可以不用迭代器：**

1. ✓ 这是一个**简单的内部类**或**私有方法**
2. ✓ **不会**对外提供 API
3. ✓ **不会**改变内部实现
4. ✓ **不需要**不同的遍历方式
5. ✓ **不需要**控制访问权限

**例如：**

```java
class Order {
    private List<Item> items = new ArrayList<>();
    
    // 内部方法，直接 loop 没问题
    private double calculateTotal() {
        double total = 0;
        for (Item item : items) {  // ✓ OK
            total += item.getPrice();
        }
        return total;
    }
    
    // 但如果要对外提供遍历，就要用迭代器
    public Iterator<Item> iterator() {
        return new ItemIterator(this);
    }
}
```

## 总结

| | 直接暴露集合 | 使用迭代器 |
|---|---|---|
| **简单性** | ✓ 更简单 | 稍复杂 |
| **封装性** | ✗ 可被修改 | ✓ 完全封装 |
| **灵活性** | ✗ 难以替换实现 | ✓ 轻松替换 |
| **可维护性** | ✗ 修改影响广 | ✓ 局部修改 |
| **扩展性** | ✗ 难以添加新遍历方式 | ✓ 轻松扩展 |

**结论：**
- 内部使用 → 直接 loop
- 对外 API → 使用迭代器

迭代器模式不是炫技，而是**防御性编程**，保护你的代码免受未来变化的影响！
