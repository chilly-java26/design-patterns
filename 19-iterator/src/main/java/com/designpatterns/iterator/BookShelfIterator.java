package com.designpatterns.iterator;

/**
 * 书架迭代器 - 具体迭代器，知道如何遍历书架
 */
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
