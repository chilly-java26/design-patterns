package com.designpatterns.observer.pull;

/**
 * 被观察者接口 - 拉模式
 * 只负责通知观察者，不主动推送数据
 */
public interface SubjectPull {
    /**
     * 添加观察者（订阅）
     */
    void attach(ObserverPull observer);
    
    /**
     * 移除观察者（取消订阅）
     */
    void detach(ObserverPull observer);
    
    /**
     * 通知所有观察者（不带具体数据）
     */
    void notifyObservers();
}
