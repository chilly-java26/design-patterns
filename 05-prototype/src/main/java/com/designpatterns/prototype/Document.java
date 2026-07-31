package com.designpatterns.prototype;

/**
 * 原型类 - 文档
 * 实现Cloneable接口，支持克隆
 */
public class Document implements Cloneable {
    private String title;
    private String content;
    
    public Document(String title, String content) {
        this.title = title;
        this.content = content;
        // 模拟创建对象的耗时操作
        System.out.println("创建文档: " + title + " (耗时操作)");
    }
    
    /**
     * 重写clone方法，实现浅克隆
     */
    @Override
    public Document clone() {
        try {
            System.out.println("克隆文档: " + this.title + " (快速操作)");
            return (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("克隆失败", e);
        }
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
