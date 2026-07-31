package com.designpatterns.factory;

/**
 * 三角形 - 新增的具体产品
 * 无需修改任何现有代码
 */
public class Triangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing: Triangle");
    }
}
