package com.designpatterns.decorator.practice;

// 抽象类，持有对象，调用对象的函数，可不实现接口
public abstract class ClothesDecoration implements Clothes {
    protected Clothes clothes;

    public ClothesDecoration(Clothes clothes) {
        this.clothes = clothes;
    }

    @Override
    public String getDecorations() {
        return clothes.getDecorations();
    }
}
