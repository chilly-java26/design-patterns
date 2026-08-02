package com.designpatterns.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * 书架类 - 聚合对象，存储书籍并提供创建迭代器的方法
 */
public class BookShelf implements Aggregate<Book> {
    private List<Book> books;

    public BookShelf() {
        this.books = new ArrayList<>();
    }

    /**
     * 添加书籍
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * 获取书籍数量
     */
    public int getLength() {
        return books.size();
    }

    /**
     * 按索引获取书籍
     */
    public Book getBookAt(int index) {
        return books.get(index);
    }

    /**
     * 创建迭代器
     */
    @Override
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }
}
