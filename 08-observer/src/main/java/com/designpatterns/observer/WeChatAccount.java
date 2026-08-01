package com.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信公众号 (ConcreteSubject)
 * 被观察者的具体实现
 */
public class WeChatAccount implements Subject {
    
    private String name;
    private List<Observer> followers;  // 粉丝列表
    
    public WeChatAccount(String name) {
        this.name = name;
        this.followers = new ArrayList<>();
    }
    
    @Override
    public void attach(Observer observer) {
        followers.add(observer);
        System.out.println("[" + name + "] 新增粉丝");
    }
    
    @Override
    public void detach(Observer observer) {
        followers.remove(observer);
        System.out.println("[" + name + "] 粉丝取关");
    }
    
    @Override
    public void notifyObservers(String message) {
        System.out.println("\n[" + name + "] 发布新文章：" + message);
        System.out.println("推送给 " + followers.size() + " 位粉丝\n");
        for (Observer follower : followers) {
            follower.update(message);
        }
    }
    
    /**
     * 发布文章
     */
    public void publishArticle(String article) {
        notifyObservers(article);
    }
    
    public String getName() {
        return name;
    }
}
