package com.designpatterns.observer;

/**
 * 观察者接口 (Observer)
 * 所有观察者都要实现这个接口
 */
public interface Observer {
    /**
     * 接收通知并更新
     * @param message 通知消息
     */
    void update(String message);
}
