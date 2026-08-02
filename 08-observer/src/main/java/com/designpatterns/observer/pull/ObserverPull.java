package com.designpatterns.observer.pull;

/**
 * 观察者接口 - 拉模式
 * 观察者主动从Subject拉取数据
 */
public interface ObserverPull {
    /**
     * 接收通知（不带数据）
     * 观察者需要主动从subject拉取数据
     */
    void update(SubjectPull subject);
}
