package com.designpatterns.decorator;

/**
 * Component - 组件接口
 * 定义了对象的基本功能
 */
public interface Coffee {
    /**
     * 获取咖啡的描述
     */
    String getDescription();
    
    /**
     * 获取咖啡的价格
     */
    double getCost();
}
