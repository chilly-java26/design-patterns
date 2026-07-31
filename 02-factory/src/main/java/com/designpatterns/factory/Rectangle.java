package com.designpatterns.factory;

/**
 * 矩形 - 具体产品
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing: Rectangle");
    }
}
