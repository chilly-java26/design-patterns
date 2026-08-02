package com.designpatterns.flyweight;

import com.designpatterns.flyweight.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 文档编辑器 - 客户端
 * 使用享元工厂获取共享对象，并管理外部状态
 */
public class Document {
    private final CharacterFactory factory;
    // 关键：Context数组存储所有字符的外部状态
    private final List<CharacterContext> characters;
    
    public Document() {
        this.factory = new CharacterFactory();
        this.characters = new ArrayList<>();
    }

    /**
     * 添加字符到文档
     * @param character 字符内容
     * @param font 字体
     * @param fontSize 字号
     * @param x 位置X
     * @param y 位置Y
     * @param color 颜色
     */
    public void addCharacter(char character, String font, int fontSize, 
                            int x, int y, String color) {
        // 关键：从工厂获取享元对象（可能复用已有对象）
        CharacterFlyweight flyweight = factory.getCharacter(character, font, fontSize);
        
        // 关键：创建Context存储外部状态
        CharacterContext context = new CharacterContext(flyweight, x, y, color);
        characters.add(context);
    }

    /**
     * 渲染整个文档
     */
    public void render() {
        System.out.println("\n" + StringUtil.repeat("=", 50));
        System.out.println("📄 渲染文档（共 " + characters.size() + " 个字符）");
        System.out.println(StringUtil.repeat("=", 50));
        
        for (CharacterContext context : characters) {
            context.render();
        }
        
        System.out.println(StringUtil.repeat("=", 50) + "\n");
    }

    /**
     * 获取文档统计信息
     */
    public void printStatistics() {
        System.out.println("📊 文档统计:");
        System.out.println("  - 总字符数: " + characters.size());
        System.out.println("  - 享元对象数: " + factory.getPoolSize());
        System.out.println("  - 平均复用次数: " + 
                String.format("%.2f", (double) characters.size() / factory.getPoolSize()));
        
        factory.printStatistics();
    }

    /**
     * 获取字符数量
     */
    public int getCharacterCount() {
        return characters.size();
    }
}
