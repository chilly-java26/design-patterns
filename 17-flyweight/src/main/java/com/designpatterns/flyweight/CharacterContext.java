package com.designpatterns.flyweight;

/**
 * 字符上下文 - 存储外部状态（变化数据）
 * 外部状态：位置、颜色（每个字符都不同）
 */
public class CharacterContext {
    // 引用享元对象（共享）
    private final CharacterFlyweight flyweight;
    
    // 外部状态：存储在这里
    private final int x;
    private final int y;
    private final String color;

    public CharacterContext(CharacterFlyweight flyweight, int x, int y, String color) {
        this.flyweight = flyweight;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * 渲染字符 - 将外部状态传递给享元对象
     */
    public void render() {
        flyweight.render(x, y, color);
    }

    public CharacterFlyweight getFlyweight() {
        return flyweight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }
}
