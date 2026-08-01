package com.designpatterns.decorator.practice;

// 装饰类，继承抽象接口，叠加对象功能
public class ButtonDecoration extends ClothesDecoration implements Clothes {
    public ButtonDecoration(Clothes clothes) {
        super(clothes);
    }

    @Override
    public String getDecorations() {
        return clothes.getDecorations() + "+Button";
    }
}
