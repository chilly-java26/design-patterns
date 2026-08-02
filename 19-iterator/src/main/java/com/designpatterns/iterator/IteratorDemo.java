package com.designpatterns.iterator;

/**
 * 迭代器模式演示
 * 
 * 迭代器模式的核心思想：
 * 提供一种方法顺序访问聚合对象中的元素，而不暴露其内部表示
 */
public class IteratorDemo {
    public static void main(String[] args) {
        System.out.println("=== 迭代器模式演示 ===\n");

        // 创建书架
        BookShelf bookShelf = new BookShelf();

        // 添加书籍
        bookShelf.addBook(new Book("设计模式：可复用面向对象软件的基础", "GoF"));
        bookShelf.addBook(new Book("重构：改善既有代码的设计", "Martin Fowler"));
        bookShelf.addBook(new Book("代码整洁之道", "Robert C. Martin"));
        bookShelf.addBook(new Book("Effective Java", "Joshua Bloch"));
        bookShelf.addBook(new Book("深入理解Java虚拟机", "周志明"));

        System.out.println("书架上的所有书籍：\n");

        // 使用迭代器遍历书架
        Iterator<Book> iterator = bookShelf.iterator();
        int count = 1;
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(count + ". " + book);
            count++;
        }

        System.out.println("\n=== 演示完成 ===");
        System.out.println("\n优势说明：");
        System.out.println("1. 封装性：客户端不需要知道书架内部的存储结构");
        System.out.println("2. 统一接口：使用 hasNext() 和 next() 统一遍历方式");
        System.out.println("3. 灵活性：改变内部实现时，客户端代码无需修改");
        System.out.println("4. 单一职责：遍历逻辑独立，书架只负责存储管理");
    }
}
