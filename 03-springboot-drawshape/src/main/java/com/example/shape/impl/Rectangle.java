package com.example.shape.impl;

import com.example.shape.Shape;
import java.beans.ConstructorProperties;

/**
 * 矩形
 */
public class Rectangle implements Shape {
    private final double width;
    private final double height;

    @ConstructorProperties({"width", "height"})
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("绘制矩形：宽 " + width + "，高 " + height +
                "，面积 " + String.format("%.2f", width * height));
    }
}
