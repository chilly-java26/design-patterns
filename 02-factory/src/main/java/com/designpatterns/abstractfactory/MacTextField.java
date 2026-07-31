package com.designpatterns.abstractfactory;

/**
 * 具体产品：Mac 文本框
 */
public class MacTextField implements TextField {
    @Override
    public void render() {
        System.out.println("渲染 Mac 风格文本框");
    }
}
