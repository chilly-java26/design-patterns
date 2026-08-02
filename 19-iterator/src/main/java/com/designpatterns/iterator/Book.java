package com.designpatterns.iterator;

/**
 * 书籍类 - 被存储的数据对象
 */
public class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "《" + title + "》 - " + author;
    }
}
