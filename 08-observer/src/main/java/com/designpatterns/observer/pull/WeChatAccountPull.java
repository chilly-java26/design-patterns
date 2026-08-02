package com.designpatterns.observer.pull;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信公众号 - 拉模式 (ConcreteSubject)
 * 被观察者的具体实现，提供数据获取方法
 */
public class WeChatAccountPull implements SubjectPull {
    
    private String name;
    private List<ObserverPull> followers;  // 粉丝列表
    
    // 公众号的状态数据（供观察者拉取）
    private String latestArticle;      // 最新文章
    private String author;             // 作者
    private int readCount;             // 阅读量
    private int likeCount;             // 点赞数
    
    public WeChatAccountPull(String name) {
        this.name = name;
        this.followers = new ArrayList<>();
    }
    
    @Override
    public void attach(ObserverPull observer) {
        followers.add(observer);
        System.out.println("[" + name + "] 新增粉丝");
    }
    
    @Override
    public void detach(ObserverPull observer) {
        followers.remove(observer);
        System.out.println("[" + name + "] 粉丝取关");
    }
    
    @Override
    public void notifyObservers() {
        System.out.println("\n[" + name + "] 发布了新内容，通知所有粉丝");
        System.out.println("共 " + followers.size() + " 位粉丝会收到通知\n");
        for (ObserverPull follower : followers) {
            // 拉模式：只通知，不传递具体数据
            // 观察者需要主动调用getter方法获取数据
            follower.update(this);
        }
    }
    
    /**
     * 发布文章
     */
    public void publishArticle(String article, String author, int readCount, int likeCount) {
        this.latestArticle = article;
        this.author = author;
        this.readCount = readCount;
        this.likeCount = likeCount;
        
        // 发布后通知所有观察者
        notifyObservers();
    }
    
    // ========== 以下是供观察者拉取数据的getter方法 ==========
    
    public String getName() {
        return name;
    }
    
    public String getLatestArticle() {
        return latestArticle;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public int getReadCount() {
        return readCount;
    }
    
    public int getLikeCount() {
        return likeCount;
    }
}
