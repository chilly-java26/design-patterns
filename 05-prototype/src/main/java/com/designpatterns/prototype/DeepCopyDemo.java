package com.designpatterns.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * 深拷贝演示
 * 深拷贝：不仅复制对象本身，还递归复制所有引用类型字段
 */
public class DeepCopyDemo {
    public static void main(String[] args) {
        System.out.println("=== 深拷贝演示 ===\n");
        
        // 1. 创建原始文档
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("设计模式");
        
        DocumentDeepCopy original = new DocumentDeepCopy("原型模式", "内容", tags);
        System.out.println("1. 原始文档:");
        System.out.println(original);
        System.out.println();
        
        // 2. 深拷贝
        DocumentDeepCopy deepCopy = original.clone();
        System.out.println("2. 深拷贝后:");
        System.out.println("原始文档: " + original);
        System.out.println("拷贝文档: " + deepCopy);
        System.out.println();
        
        // 3. 修改拷贝文档的标签列表
        System.out.println("3. 修改拷贝文档的标签列表:");
        deepCopy.getTags().add("深拷贝");
        System.out.println("原始文档标签: " + original.getTags());
        System.out.println("拷贝文档标签: " + deepCopy.getTags());
        System.out.println("✓ 引用类型字段也独立了，互不影响！");
        System.out.println();
        
        // 4. 验证引用地址
        System.out.println("4. 验证tags引用是否相同:");
        System.out.println("original.getTags() == deepCopy.getTags(): " 
            + (original.getTags() == deepCopy.getTags()));
        System.out.println("深拷贝创建了新的List对象，完全独立！");
    }
}

/**
 * 包含引用类型字段的文档类（深拷贝版本）
 */
class DocumentDeepCopy implements Cloneable {
    private String title;
    private String content;
    private List<String> tags;
    
    public DocumentDeepCopy(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }
    
    @Override
    public DocumentDeepCopy clone() {
        try {
            // 先调用父类的clone（浅拷贝）
            DocumentDeepCopy cloned = (DocumentDeepCopy) super.clone();
            
            // 手动深拷贝引用类型字段
            cloned.tags = new ArrayList<>(this.tags);
            
            return cloned;
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
        return "DocumentDeepCopy{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                '}';
    }
}
