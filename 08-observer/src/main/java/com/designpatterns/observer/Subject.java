package com.designpatterns.observer;

/**
 * 被观察者接口 (Subject)
 * 定义了观察者的管理和通知方法
 */
public interface Subject {
    /**
     * 添加观察者（订阅）
     */
    void attach(Observer observer);
    
    /**
     * 移除观察者（取消订阅）
     */
    void detach(Observer observer);
    
    /**
     * 通知所有观察者
     */
    void notifyObservers(String message);
}
