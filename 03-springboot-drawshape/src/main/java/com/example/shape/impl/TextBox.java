package com.example.shape.impl;

import com.example.shape.Shape;
import java.beans.ConstructorProperties;

/**
 * 文本框 - 演示支持多种类型（int, double, String）
 */
public class TextBox implements Shape {
    private final String text;
    private final int fontSize;
    private final double opacity;

    @ConstructorProperties({"text", "fontSize", "opacity"})
    public TextBox(String text, int fontSize, double opacity) {
        this.text = text;
        this.fontSize = fontSize;
        this.opacity = opacity;
    }

    @Override
    public void draw() {
        System.out.println("绘制文本框：文本='" + text + 
            "'，字体大小=" + fontSize + 
            "，透明度=" + String.format("%.2f", opacity));
    }
}
