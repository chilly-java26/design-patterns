package com.designpatterns.decorator.practice;

// 简单实现只实现接口，作为初始参数
public class SimpleClothes implements Clothes {
    @Override
    public String getDecorations() {
        return "Simple Clothes";
    }
}
