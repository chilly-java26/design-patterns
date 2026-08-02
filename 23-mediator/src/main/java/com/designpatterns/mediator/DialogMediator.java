package com.designpatterns.mediator;

/**
 * 中介者接口
 * 定义了组件之间通信的契约
 */
public interface DialogMediator {
    /**
     * 处理组件发送的通知
     * 
     * @param sender 发送通知的组件
     * @param event 事件类型
     */
    void notify(Component sender, String event);
}
