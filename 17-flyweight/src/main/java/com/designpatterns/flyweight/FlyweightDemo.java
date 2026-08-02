package com.designpatterns.flyweight;

import com.designpatterns.flyweight.util.StringUtil;

/**
 * 享元模式示例 - 文档编辑器
 * 
 * 场景：模拟在线文档编辑器输入大量文字
 * 
 * 关键概念：
 * 1. 内部状态（共享）：字符、字体、字号 -> 存在享元对象内部
 * 2. 外部状态（不共享）：位置、颜色 -> 通过参数传入
 * 3. 缓存池：用Map管理共享对象
 * 4. Context数组：存储外部状态和享元对象引用
 * 
 * 效果：
 * - 不用享元：10万字符 = 10万个完整对象
 * - 用享元：10万字符 = 几百个享元 + 10万个轻量Context
 */
public class FlyweightDemo {
    
    public static void main(String[] args) {
        System.out.println("🎯 享元模式示例 - 文档编辑器字符渲染系统\n");
        
        // 示例1：基本用法
        basicExample();
        
        // 示例2：大量字符模拟
        massiveCharactersExample();
        
        // 示例3：对比测试
        comparisonExample();
    }

    /**
     * 示例1：基本用法演示
     */
    private static void basicExample() {
        System.out.println("\n" + StringUtil.repeat("═", 60));
        System.out.println("📝 示例1：基本用法 - 输入 'HELLO'");
        System.out.println(StringUtil.repeat("═", 60) + "\n");
        
        Document doc = new Document();
        
        // 输入 "HELLO" - 注意 'L' 会被复用
        doc.addCharacter('H', "Arial", 12, 0, 0, "Black");
        doc.addCharacter('E', "Arial", 12, 10, 0, "Black");
        doc.addCharacter('L', "Arial", 12, 20, 0, "Black");
        doc.addCharacter('L', "Arial", 12, 30, 0, "Black");  // 复用 'L'
        doc.addCharacter('O', "Arial", 12, 40, 0, "Black");
        
        doc.render();
        doc.printStatistics();
    }

    /**
     * 示例2：大量字符模拟
     */
    private static void massiveCharactersExample() {
        System.out.println("\n" + StringUtil.repeat("═", 60));
        System.out.println("📝 示例2：大量字符 - 模拟输入段落");
        System.out.println(StringUtil.repeat("═", 60) + "\n");
        
        Document doc = new Document();
        
        // 模拟输入一段文字（重复的文字会触发享元复用）
        String text = "The quick brown fox jumps over the lazy dog. ";
        String[] colors = {"Black", "Red", "Blue", "Green"};
        
        int x = 0, y = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String color = colors[i % colors.length];
            
            doc.addCharacter(c, "宋体", 14, x, y, color);
            
            x += 10;
            if (x > 400) {
                x = 0;
                y += 20;
            }
        }
        
        System.out.println("\n分析：");
        System.out.println("- 输入了 " + text.length() + " 个字符");
        System.out.println("- 但实际只创建了很少的享元对象");
        System.out.println("- 因为很多字符（如空格、字母）是重复的\n");
        
        doc.printStatistics();
    }

    /**
     * 示例3：对比测试 - 享元 vs 非享元
     */
    private static void comparisonExample() {
        System.out.println("\n" + StringUtil.repeat("═", 60));
        System.out.println("📊 示例3：性能对比 - 享元 vs 非享元");
        System.out.println(StringUtil.repeat("═", 60) + "\n");
        
        // 模拟输入1000个字符
        int charCount = 1000;
        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        System.out.println("场景：输入 " + charCount + " 个字符\n");
        
        // 使用享元模式
        Document flyweightDoc = new Document();
        long startTime = System.nanoTime();
        
        for (int i = 0; i < charCount; i++) {
            char c = text.charAt(i % text.length());
            flyweightDoc.addCharacter(c, "Arial", 12, i * 10, 0, "Black");
        }
        
        long flyweightTime = System.nanoTime() - startTime;
        
        System.out.println("✅ 使用享元模式:");
        System.out.println("  - 实际创建对象数: " + flyweightDoc.getCharacterCount() + " Context + " 
                + "享元池中的对象");
        flyweightDoc.printStatistics();
        
        // 模拟非享元模式（每次都创建新对象）
        System.out.println("❌ 不使用享元模式:");
        System.out.println("  - 需要创建对象数: " + charCount + " 个完整对象");
        System.out.println("  - 每个对象包含：字符+字体+字号+位置+颜色");
        System.out.println("  - 内存占用是享元模式的数十倍\n");
        
        System.out.println("💡 结论:");
        System.out.println("  享元模式通过共享内部状态，大幅减少了对象数量");
        System.out.println("  特别适合大量细粒度对象的场景\n");
    }
}
