package com.example.shape.impl;

import com.example.shape.Shape;
import java.beans.ConstructorProperties;

/**
 * 圆形
 */
public class Circle implements Shape {
    private final double radius;

    @ConstructorProperties({"radius"})
    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("绘制圆形：半径 " + radius + 
            "，面积 " + String.format("%.2f", Math.PI * radius * radius));
    }
}
