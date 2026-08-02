package com.designpatterns.iterator.comparison;

import com.designpatterns.iterator.*;

/**
 * 使用迭代器模式的优势演示
 */
public class WithIteratorDemo {
    
    // 使用 List 实现的书架
    static class ListBookShelf implements Aggregate<Book> {
        private java.util.List<Book> books = new java.util.ArrayList<>();
        
        public void addBook(Book book) {
            books.add(book);
        }
        
        @Override
        public Iterator<Book> iterator() {
            return new Iterator<Book>() {
                private int index = 0;
                
                @Override
                public boolean hasNext() {
                    return index < books.size();
                }
                
                @Override
                public Book next() {
                    return books.get(index++);
                }
            };
        }
    }
    
    // 使用数组实现的书架
    static class ArrayBookShelf implements Aggregate<Book> {
        private Book[] books;
        private int size;
        
        public ArrayBookShelf(int capacity) {
            books = new Book[capacity];
            size = 0;
        }
        
        public void addBook(Book book) {
            books[size++] = book;
        }
        
        @Override
        public Iterator<Book> iterator() {
            return new Iterator<Book>() {
                private int index = 0;
                
                @Override
                public boolean hasNext() {
                    return index < size;
                }
                
                @Override
                public Book next() {
                    return books[index++];
                }
            };
        }
    }
    
    // 通用的打印方法 - 不关心内部实现
    static void printBooks(Aggregate<Book> bookShelf, String label) {
        System.out.println(label);
        Iterator<Book> iterator = bookShelf.iterator();
        int count = 1;
        while (iterator.hasNext()) {
            System.out.println("  " + count++ + ". " + iterator.next().getTitle());
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== 使用迭代器模式的优势 ===\n");
        
        // 优势1: 完全封装，无法直接修改
        System.out.println("【优势1】完全封装内部结构：");
        ListBookShelf shelf1 = new ListBookShelf();
        shelf1.addBook(new Book("设计模式", "GoF"));
        // shelf1.books  // 编译错误！无法访问
        // shelf1.getBooks()  // 没有这个方法
        System.out.println("✓ 客户端无法直接访问内部集合");
        System.out.println("✓ 只能通过迭代器安全访问\n");
        
        // 优势2: 统一的访问接口
        System.out.println("【优势2】统一的访问接口：");
        
        // List 实现
        ListBookShelf listShelf = new ListBookShelf();
        listShelf.addBook(new Book("Java编程思想", "Bruce Eckel"));
        listShelf.addBook(new Book("Effective Java", "Joshua Bloch"));
        
        // 数组实现
        ArrayBookShelf arrayShelf = new ArrayBookShelf(10);
        arrayShelf.addBook(new Book("重构", "Martin Fowler"));
        arrayShelf.addBook(new Book("代码整洁之道", "Robert Martin"));
        
        // 相同的遍历代码！
        printBooks(listShelf, "List 实现的书架：");
        printBooks(arrayShelf, "数组实现的书架：");
        
        System.out.println("✓ 无论内部用 List 还是数组，客户端代码完全一样");
        System.out.println("✓ 符合依赖倒置原则（依赖抽象而非具体实现）\n");
        
        // 优势3: 灵活的扩展
        System.out.println("【优势3】轻松支持多种遍历方式：");
        
        // 可以为同一个集合提供不同的迭代器
        class FlexibleBookShelf implements Aggregate<Book> {
            private java.util.List<Book> books = new java.util.ArrayList<>();
            
            public void addBook(Book book) {
                books.add(book);
            }
            
            // 正向迭代器
            @Override
            public Iterator<Book> iterator() {
                return new Iterator<Book>() {
                    private int index = 0;
                    public boolean hasNext() { return index < books.size(); }
                    public Book next() { return books.get(index++); }
                };
            }
            
            // 反向迭代器
            public Iterator<Book> reverseIterator() {
                return new Iterator<Book>() {
                    private int index = books.size() - 1;
                    public boolean hasNext() { return index >= 0; }
                    public Book next() { return books.get(index--); }
                };
            }
        }
        
        FlexibleBookShelf flexShelf = new FlexibleBookShelf();
        flexShelf.addBook(new Book("第一本", "作者A"));
        flexShelf.addBook(new Book("第二本", "作者B"));
        flexShelf.addBook(new Book("第三本", "作者C"));
        
        System.out.println("正向遍历：");
        Iterator<Book> forward = flexShelf.iterator();
        while (forward.hasNext()) {
            System.out.println("  " + forward.next().getTitle());
        }
        
        System.out.println("\n反向遍历：");
        Iterator<Book> reverse = flexShelf.reverseIterator();
        while (reverse.hasNext()) {
            System.out.println("  " + reverse.next().getTitle());
        }
        
        System.out.println("\n✓ 同一个集合，多种遍历方式");
        System.out.println("✓ 不需要修改集合类本身\n");
        
        // 优势4: 多个独立的遍历过程
        System.out.println("【优势4】支持多个独立的遍历：");
        Iterator<Book> it1 = listShelf.iterator();
        Iterator<Book> it2 = listShelf.iterator();
        
        System.out.println("迭代器1 第一步: " + it1.next().getTitle());
        System.out.println("迭代器2 第一步: " + it2.next().getTitle());
        System.out.println("迭代器1 第二步: " + it1.next().getTitle());
        System.out.println("✓ 两个迭代器互不影响，各自维护遍历状态\n");
        
        // 总结
        System.out.println("=== 总结 ===");
        System.out.println("迭代器模式不是为了炫技，而是为了：");
        System.out.println("1. 让代码更安全（封装）");
        System.out.println("2. 让代码更灵活（可替换实现）");
        System.out.println("3. 让代码更简洁（统一接口）");
        System.out.println("4. 让代码更易维护（职责分离）");
    }
}
