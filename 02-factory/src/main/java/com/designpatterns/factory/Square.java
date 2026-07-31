package com.designpatterns.factory;

/**
 * 正方形 - 具体产品
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing: Square");
    }
}
