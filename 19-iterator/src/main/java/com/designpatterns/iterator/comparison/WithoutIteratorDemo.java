package com.designpatterns.iterator.comparison;

import com.designpatterns.iterator.Book;
import java.util.List;
import java.util.ArrayList;

/**
 * 不使用迭代器模式的问题演示
 */
public class WithoutIteratorDemo {
    
    // 场景1: 暴露内部结构
    static class BookShelfV1 {
        public List<Book> books = new ArrayList<>();  // 直接暴露
        
        public void addBook(Book book) {
            books.add(book);
        }
    }
    
    // 场景2: 提供 getter
    static class BookShelfV2 {
        private List<Book> books = new ArrayList<>();
        
        public void addBook(Book book) {
            books.add(book);
        }
        
        public List<Book> getBooks() {  // 返回内部集合
            return books;
        }
    }
    
    // 场景3: 如果内部改用数组呢？
    static class BookShelfV3 {
        private Book[] books;
        private int size;
        
        public BookShelfV3(int capacity) {
            books = new Book[capacity];
            size = 0;
        }
        
        public void addBook(Book book) {
            books[size++] = book;
        }
        
        // 现在客户端代码需要改变！
        public Book[] getBooks() {  // 返回类型变了
            return books;
        }
        
        public int getSize() {
            return size;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 不使用迭代器模式的问题 ===\n");
        
        // 问题1: 直接暴露内部结构
        System.out.println("【问题1】直接暴露内部结构：");
        BookShelfV1 shelf1 = new BookShelfV1();
        shelf1.addBook(new Book("设计模式", "GoF"));
        
        // 客户端可以直接修改！破坏了封装
        shelf1.books.clear();  // 糟糕！直接清空了
        shelf1.books.add(new Book("黑客与画家", "Paul Graham"));  // 绕过了 addBook
        System.out.println("✗ 客户端可以直接修改内部集合，破坏封装");
        System.out.println("✗ 无法控制访问权限和验证逻辑\n");
        
        // 问题2: 提供 getter 依然有问题
        System.out.println("【问题2】提供 getter 返回内部集合：");
        BookShelfV2 shelf2 = new BookShelfV2();
        shelf2.addBook(new Book("设计模式", "GoF"));
        shelf2.addBook(new Book("重构", "Martin Fowler"));
        
        List<Book> bookList = shelf2.getBooks();
        bookList.clear();  // 依然可以修改！
        System.out.println("✗ 返回内部集合的引用，客户端仍可修改");
        System.out.println("✗ 客户端代码依赖 List 类型，耦合度高\n");
        
        // 问题3: 修改内部实现导致客户端代码全部改变
        System.out.println("【问题3】修改内部实现（List 改为数组）：");
        
        // 使用 List 版本的客户端代码
        BookShelfV2 shelf2a = new BookShelfV2();
        shelf2a.addBook(new Book("Java编程思想", "Bruce Eckel"));
        List<Book> list = shelf2a.getBooks();
        for (Book book : list) {  // 依赖 List 的 for-each
            System.out.println("  " + book.getTitle());
        }
        
        System.out.println("\n如果改用数组实现：");
        
        // 使用数组版本 - 客户端代码必须改变！
        BookShelfV3 shelf3 = new BookShelfV3(10);
        shelf3.addBook(new Book("Java编程思想", "Bruce Eckel"));
        Book[] array = shelf3.getBooks();  // 类型变了
        for (int i = 0; i < shelf3.getSize(); i++) {  // 遍历方式也变了
            if (array[i] != null) {
                System.out.println("  " + array[i].getTitle());
            }
        }
        System.out.println("✗ 客户端代码必须全部修改");
        System.out.println("✗ 违反了开闭原则\n");
        
        // 问题4: 不同的遍历方式需要暴露更多内部信息
        System.out.println("【问题4】需要不同遍历方式时：");
        System.out.println("如果要反向遍历、随机遍历、过滤遍历...");
        System.out.println("✗ 需要暴露更多内部方法");
        System.out.println("✗ 客户端代码会变得很复杂");
        System.out.println("✗ 遍历逻辑分散在各个客户端中\n");
        
        // 总结
        System.out.println("=== 总结：为什么需要迭代器模式 ===");
        System.out.println("1. 封装性：隐藏内部实现细节");
        System.out.println("2. 灵活性：改变内部实现不影响客户端");
        System.out.println("3. 统一性：提供统一的遍历接口");
        System.out.println("4. 单一职责：遍历逻辑集中管理");
        System.out.println("5. 可扩展性：轻松支持多种遍历方式");
    }
}
