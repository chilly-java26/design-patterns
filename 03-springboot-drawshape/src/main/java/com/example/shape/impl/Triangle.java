package com.example.shape.impl;

import com.example.shape.Shape;
import java.beans.ConstructorProperties;

/**
 * 三角形
 */
public class Triangle implements Shape {
    private final double base;
    private final double height;

    @ConstructorProperties({"base", "height"})
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("绘制三角形：底 " + base + "，高 " + height + 
            "，面积 " + String.format("%.2f", 0.5 * base * height));
    }
}
