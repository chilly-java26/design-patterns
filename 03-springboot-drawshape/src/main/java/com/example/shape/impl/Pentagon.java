package com.example.shape.impl;

import com.example.shape.Shape;

import java.beans.ConstructorProperties;

public class Pentagon implements Shape {
    private final double side;

    @ConstructorProperties({"side"})
    public Pentagon(double side) {
        this.side = side;
    }

    @Override
    public void draw() {
        System.out.println("绘制五边形：边长 " + side +
                "，面积 " + String.format("%.2f", (1.72048 * side * side)));
    }
}
