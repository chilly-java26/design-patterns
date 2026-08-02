package com.designpatterns.flyweight;

/**
 * 享元对象 - 存储内部状态（共享数据）
 * 内部状态：字符、字体、字号（不可变）
 */
public class CharacterFlyweight {
    // 内部状态：存储在对象内部，不可变
    private final char character;
    private final String font;
    private final int fontSize;

    public CharacterFlyweight(char character, String font, int fontSize) {
        this.character = character;
        this.font = font;
        this.fontSize = fontSize;
    }

    /**
     * 渲染方法 - 外部状态通过参数传入
     * @param x 位置X（外部状态）
     * @param y 位置Y（外部状态）
     * @param color 颜色（外部状态）
     */
    public void render(int x, int y, String color) {
        System.out.printf("渲染字符 '%c' [字体=%s, 字号=%dpt] 在位置(%d,%d) 颜色=%s%n",
                character, font, fontSize, x, y, color);
    }

    public char getCharacter() {
        return character;
    }

    public String getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }

    @Override
    public String toString() {
        return String.format("Character{%c, %s, %dpt}", character, font, fontSize);
    }
}
