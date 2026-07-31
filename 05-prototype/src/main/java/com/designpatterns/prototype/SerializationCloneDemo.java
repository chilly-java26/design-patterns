package com.designpatterns.prototype;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过序列化实现深拷贝
 * 这是实现深拷贝的另一种方式，特别适合复杂对象图
 */
public class SerializationCloneDemo {
    public static void main(String[] args) {
        System.out.println("=== 序列化深拷贝演示 ===\n");
        
        // 1. 创建复杂对象
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("设计模式");
        
        Author author = new Author("张三", "zhang@example.com");
        
        ComplexDocument original = new ComplexDocument(
            "原型模式详解", 
            "这是一篇深入讲解原型模式的文档",
            tags,
            author
        );
        
        System.out.println("1. 原始文档:");
        System.out.println(original);
        System.out.println();
        
        // 2. 通过序列化深拷贝
        ComplexDocument deepCopy = original.deepClone();
        System.out.println("2. 序列化深拷贝后:");
        System.out.println(deepCopy);
        System.out.println();
        
        // 3. 修改所有字段
        System.out.println("3. 修改拷贝文档的所有字段:");
        deepCopy.setTitle("原型模式详解-副本");
        deepCopy.getTags().add("序列化");
        deepCopy.getAuthor().setName("李四");
        
        System.out.println("原始文档: " + original);
        System.out.println("拷贝文档: " + deepCopy);
        System.out.println();
        
        // 4. 验证所有引用都是独立的
        System.out.println("4. 验证所有引用独立性:");
        System.out.println("tags引用相同? " + (original.getTags() == deepCopy.getTags()));
        System.out.println("author引用相同? " + (original.getAuthor() == deepCopy.getAuthor()));
        System.out.println("✓ 所有引用类型字段都完全独立！");
    }
}

/**
 * 作者类
 */
class Author implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String email;
    
    public Author(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Author{name='" + name + "', email='" + email + "'}";
    }
}

/**
 * 复杂文档类 - 包含多个引用类型字段
 */
class ComplexDocument implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String title;
    private String content;
    private List<String> tags;
    private Author author;
    
    public ComplexDocument(String title, String content, List<String> tags, Author author) {
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.author = author;
    }
    
    /**
     * 通过序列化实现深拷贝
     * 优点：简单，自动处理复杂对象图
     * 缺点：性能较低，所有相关类必须实现Serializable
     */
    public ComplexDocument deepClone() {
        try {
            // 序列化到字节数组
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            
            // 从字节数组反序列化
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (ComplexDocument) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("深拷贝失败", e);
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
    
    public List<String> getTags() {
        return tags;
    }
    
    public Author getAuthor() {
        return author;
    }
    
    @Override
    public String toString() {
        return "ComplexDocument{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", author=" + author +
                '}';
    }
}
