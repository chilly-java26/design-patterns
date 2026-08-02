package com.designpatterns.mediator;

/**
 * 组件基类
 * 所有 UI 组件都持有中介者的引用，但彼此不相互引用
 */
public abstract class Component {
    protected DialogMediator mediator;
    
    public Component(DialogMediator mediator) {
        this.mediator = mediator;
    }
}
