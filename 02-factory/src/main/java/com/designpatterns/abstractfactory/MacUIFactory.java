package com.designpatterns.abstractfactory;

/**
 * 具体工厂：Mac UI 工厂
 * 创建一系列 Mac 风格的 UI 组件
 */
public enum MacUIFactory implements UIFactory {
    INSTANCE;
    
    @Override
    public Button createButton() {
        return new MacButton();
    }
    
    @Override
    public TextField createTextField() {
        return new MacTextField();
    }
}
