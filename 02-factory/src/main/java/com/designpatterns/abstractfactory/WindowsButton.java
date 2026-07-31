package com.designpatterns.abstractfactory;

/**
 * 具体产品：Windows 按钮
 */
public class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("渲染 Windows 风格按钮");
    }
}
