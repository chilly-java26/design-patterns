package com.designpatterns.factory;

/**
 * 圆形 - 具体产品
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing: Circle");
    }
}
