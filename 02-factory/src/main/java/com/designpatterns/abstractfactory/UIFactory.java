package com.designpatterns.abstractfactory;

/**
 * 抽象工厂：定义创建一系列相关产品的接口
 */
public interface UIFactory {
    /**
     * 创建按钮
     */
    Button createButton();
    
    /**
     * 创建文本框
     */
    TextField createTextField();
}
