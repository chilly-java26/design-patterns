package com.designpatterns.abstractfactory;

/**
 * 具体工厂：Windows UI 工厂
 * 创建一系列 Windows 风格的 UI 组件
 */
public enum WindowsUIFactory implements UIFactory {
    INSTANCE;
    
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public TextField createTextField() {
        return new WindowsTextField();
    }
}
