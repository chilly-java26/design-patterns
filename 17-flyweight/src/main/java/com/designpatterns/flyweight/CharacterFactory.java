package com.designpatterns.flyweight;

import com.designpatterns.flyweight.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂 - 管理共享对象池
 * 关键：用内部状态作为缓存key，实现对象复用
 */
public class CharacterFactory {
    // 缓存池：存储所有共享的享元对象
    private final Map<String, CharacterFlyweight> flyweights = new HashMap<>();
    
    // 统计信息
    private int createCount = 0;
    private int reuseCount = 0;

    /**
     * 获取字符享元对象
     * 如果已存在则复用，否则创建新对象
     */
    public CharacterFlyweight getCharacter(char character, String font, int fontSize) {
        // 关键：用内部状态构造缓存key
        String key = buildKey(character, font, fontSize);
        
        // 关键：检查缓存池中是否已存在
        if (!flyweights.containsKey(key)) {
            // 不存在：创建新享元对象
            CharacterFlyweight flyweight = new CharacterFlyweight(character, font, fontSize);
            flyweights.put(key, flyweight);
            createCount++;
            System.out.println("✨ 创建新享元: " + key);
        } else {
            // 已存在：复用
            reuseCount++;
            System.out.println("♻️  复用享元: " + key);
        }
        
        return flyweights.get(key);
    }

    /**
     * 构造缓存key - 基于内部状态
     */
    private String buildKey(char character, String font, int fontSize) {
        return character + "_" + font + "_" + fontSize;
    }

    /**
     * 获取缓存池大小
     */
    public int getPoolSize() {
        return flyweights.size();
    }

    /**
     * 打印统计信息
     */
    public void printStatistics() {
        System.out.println("\n" + StringUtil.repeat("=", 50));
        System.out.println("📊 享元工厂统计信息");
        System.out.println(StringUtil.repeat("=", 50));
        System.out.println("缓存池大小: " + flyweights.size() + " 个享元对象");
        System.out.println("创建次数: " + createCount);
        System.out.println("复用次数: " + reuseCount);
        System.out.println("复用率: " + String.format("%.2f%%", 
                reuseCount * 100.0 / (createCount + reuseCount)));
        System.out.println("内存优化: 减少了 " + reuseCount + " 个对象的创建");
        System.out.println(StringUtil.repeat("=", 50) + "\n");
    }
}
