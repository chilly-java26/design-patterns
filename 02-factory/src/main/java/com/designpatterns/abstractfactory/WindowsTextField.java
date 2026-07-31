package com.designpatterns.abstractfactory;

/**
 * 具体产品：Windows 文本框
 */
public class WindowsTextField implements TextField {
    @Override
    public void render() {
        System.out.println("渲染 Windows 风格文本框");
    }
}
