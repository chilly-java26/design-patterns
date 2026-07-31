package com.designpatterns.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * 浅拷贝问题演示
 * 浅拷贝：只复制对象本身和基本类型字段，引用类型字段只复制引用地址
 */
public class ShallowCopyDemo {
    public static void main(String[] args) {
        System.out.println("=== 浅拷贝问题演示 ===\n");
        
        // 1. 创建原始文档，包含标签列表（引用类型）
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("设计模式");
        
        DocumentWithTags original = new DocumentWithTags("原型模式", "内容", tags);
        System.out.println("1. 原始文档:");
        System.out.println(original);
        System.out.println();
        
        // 2. 浅拷贝
        DocumentWithTags shallowCopy = original.clone();
        System.out.println("2. 浅拷贝后:");
        System.out.println("原始文档: " + original);
        System.out.println("拷贝文档: " + shallowCopy);
        System.out.println();
        
        // 3. 修改基本类型字段（String是不可变的，所以像基本类型）
        System.out.println("3. 修改拷贝文档的标题（String字段）:");
        shallowCopy.setTitle("原型模式-副本");
        System.out.println("原始文档标题: " + original.getTitle());
        System.out.println("拷贝文档标题: " + shallowCopy.getTitle());
        System.out.println("✓ String字段独立，互不影响");
        System.out.println();
        
        // 4. 修改引用类型字段 - 这里会暴露浅拷贝的问题！
        System.out.println("4. 修改拷贝文档的标签列表（引用类型字段）:");
        shallowCopy.getTags().add("浅拷贝");
        System.out.println("原始文档标签: " + original.getTags());
        System.out.println("拷贝文档标签: " + shallowCopy.getTags());
        System.out.println("✗ 引用类型字段共享，修改一个影响另一个！");
        System.out.println();
        
        // 5. 验证引用地址
        System.out.println("5. 验证tags引用是否相同:");
        System.out.println("original.getTags() == shallowCopy.getTags(): " 
            + (original.getTags() == shallowCopy.getTags()));
        System.out.println("这就是浅拷贝的问题：引用类型字段指向同一个对象！");
    }
}

/**
 * 包含引用类型字段的文档类（浅拷贝版本）
 */
class DocumentWithTags implements Cloneable {
    private String title;      // String是不可变的
    private String content;    // String是不可变的
    private List<String> tags; // List是可变的引用类型
    
    public DocumentWithTags(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }
    
    @Override
    public DocumentWithTags clone() {
        try {
            // 浅拷贝：只复制对象本身，引用类型字段还是指向同一个对象
            return (DocumentWithTags) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
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
    
    @Override
    public String toString() {
        return "DocumentWithTags{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                '}';
    }
}
