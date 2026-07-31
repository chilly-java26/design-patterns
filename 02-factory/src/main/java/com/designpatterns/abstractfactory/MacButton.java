package com.designpatterns.abstractfactory;

/**
 * 具体产品：Mac 按钮
 */
public class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("渲染 Mac 风格按钮");
    }
}
