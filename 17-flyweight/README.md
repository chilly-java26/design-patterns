# 享元模式（Flyweight Pattern）

## 一句话描述

**通过共享技术复用大量细粒度对象，减少内存开销。**

## 核心概念

### 内部状态 vs 外部状态

| 类型 | 定义 | 存储位置 | 示例 |
|------|------|---------|------|
| **内部状态** | 不随环境变化的共享数据 | 享元对象内部（final） | 字符、字体、字号 |
| **外部状态** | 随使用场景变化的数据 | 通过方法参数传入 | 位置、颜色、选中状态 |

### 两个关键存储策略

```
享元模式 = 缓存池（存内部状态） + Context数组（存外部状态）
```

1. **缓存池**：用 Map 管理共享对象，相同内部状态只创建一次
2. **Context数组**：每个 Context 引用享元对象 + 存储外部状态

## 项目结构

```
17-flyweight/
├── CharacterFlyweight.java      # 享元对象（存内部状态）
├── CharacterFactory.java        # 享元工厂（管理缓存池）
├── CharacterContext.java        # 上下文（存外部状态）
├── Document.java                # 文档编辑器（客户端）
└── FlyweightDemo.java          # 示例演示
```

## 核心代码解析

### 1. 享元对象（只存内部状态）

```java
public class CharacterFlyweight {
    // ✅ 内部状态：存在对象内部，不可变
    private final char character;
    private final String font;
    private final int fontSize;
    
    // ❌ 外部状态：不存在这里
    // private int x, y;  // 错误示范
    
    // 关键：外部状态通过参数传入
    public void render(int x, int y, String color) {
        // 使用内部状态 + 外部状态
    }
}
```

### 2. 享元工厂（管理缓存池）

```java
public class CharacterFactory {
    // 关键：缓存池
    private Map<String, CharacterFlyweight> flyweights = new HashMap<>();
    
    public CharacterFlyweight getCharacter(char c, String font, int size) {
        // 关键：用内部状态作为 key
        String key = c + "_" + font + "_" + size;
        
        // 关键：检查缓存，不存在才创建
        if (!flyweights.containsKey(key)) {
            flyweights.put(key, new CharacterFlyweight(c, font, size));
        }
        
        return flyweights.get(key);
    }
}
```

### 3. Context（存外部状态）

```java
public class CharacterContext {
    private final CharacterFlyweight flyweight;  // 引用享元
    private final int x, y;                      // 外部状态
    private final String color;                  // 外部状态
    
    public void render() {
        flyweight.render(x, y, color);  // 传入外部状态
    }
}
```

### 4. 客户端（组织调用）

```java
public class Document {
    private CharacterFactory factory;
    private List<CharacterContext> characters;  // Context数组
    
    public void addCharacter(char c, String font, int size, 
                            int x, int y, String color) {
        // 从工厂获取享元（可能复用）
        CharacterFlyweight flyweight = factory.getCharacter(c, font, size);
        
        // 创建Context存储外部状态
        CharacterContext context = new CharacterContext(flyweight, x, y, color);
        characters.add(context);
    }
}
```

## 运行示例

```bash
cd 17-flyweight
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.flyweight.FlyweightDemo"
```

## 输出示例

```
📝 示例1：基本用法 - 输入 'HELLO'

✨ 创建新享元: H_Arial_12
✨ 创建新享元: E_Arial_12
✨ 创建新享元: L_Arial_12
♻️  复用享元: L_Arial_12  <- 第二个'L'复用了第一个
✨ 创建新享元: O_Arial_12

📄 渲染文档（共 5 个字符）
渲染字符 'H' [字体=Arial, 字号=12pt] 在位置(0,0) 颜色=Black
渲染字符 'E' [字体=Arial, 字号=12pt] 在位置(10,0) 颜色=Black
渲染字符 'L' [字体=Arial, 字号=12pt] 在位置(20,0) 颜色=Black
渲染字符 'L' [字体=Arial, 字号=12pt] 在位置(30,0) 颜色=Black
渲染字符 'O' [字体=Arial, 字号=12pt] 在位置(40,0) 颜色=Black

📊 享元工厂统计信息
缓存池大小: 4 个享元对象
创建次数: 4
复用次数: 1
复用率: 20.00%
```

## 效果对比

### 不使用享元模式

```
10,000 个字符 = 10,000 个完整对象
每个对象包含：字符 + 字体 + 字号 + 位置 + 颜色
内存占用：巨大
```

### 使用享元模式

```
10,000 个字符 = 几百个享元对象 + 10,000 个轻量 Context
享元对象：字符 + 字体 + 字号（共享）
Context：享元引用 + 位置 + 颜色（独立）
内存占用：减少 90%+
```

## 与原型模式的区别

| 模式 | 本质 | 对象关系 | 使用场景 |
|------|------|---------|---------|
| **享元** | 共享一个对象 | 多个引用指向同一对象 | 大量相似对象，内存敏感 |
| **原型** | 克隆多个对象 | 每次产生独立副本 | 对象创建成本高 |

**形象比喻**：
- 享元：图书馆（一本书被多人借阅）📚
- 原型：复印机（每次复印新副本）📄

## 适用场景

1. **大量细粒度对象**：系统中存在大量相似对象
2. **内存敏感**：对象占用内存较大，需要优化
3. **状态可分离**：能明确区分内部状态和外部状态
4. **外部状态可传入**：外部状态可以通过参数传递

## 经典应用

- **Java String 常量池**：相同字符串字面量共享同一对象
- **Integer 缓存池**：-128 到 127 的整数对象被缓存
- **游戏开发**：子弹、粒子效果的复用
- **GUI 系统**：字体、图标的共享

## 优缺点

### 优点

✅ 大幅减少对象数量，节省内存  
✅ 提高系统性能（减少 GC 压力）  
✅ 外部状态独立，保持灵活性

### 缺点

❌ 增加系统复杂度（需要分离内外部状态）  
❌ 需要维护缓存池（额外的管理成本）  
❌ 外部状态传递会增加调用复杂度

## 关键要点

1. **内部状态必须不可变**（用 final 修饰）
2. **缓存池用 Map 管理**，key 基于内部状态
3. **外部状态不存储在享元对象中**，通过参数传入
4. **Context 存储外部状态** + 享元对象引用
5. **享元工厂负责对象复用**，客户端只管使用
