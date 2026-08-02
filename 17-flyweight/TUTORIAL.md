# 享元模式教程

## 快速开始

```bash
cd 17-flyweight
mvn clean compile
java -cp target/classes com.designpatterns.flyweight.FlyweightDemo
```

## 核心要点

### 1. 享元模式的本质

**一句话**：通过共享技术复用大量细粒度对象，减少内存开销。

**公式**：
```
享元模式 = 缓存池（共享不变的） + Context数组（存储变化的）
```

### 2. 内部状态 vs 外部状态

#### 内部状态（共享）
- **存储位置**：享元对象内部（final 不可变）
- **特点**：所有实例相同，可以共享
- **示例**：字符内容、字体、字号

```java
class CharacterFlyweight {
    private final char character;  // 内部状态
    private final String font;     // 内部状态
    private final int fontSize;    // 内部状态
}
```

#### 外部状态（不共享）
- **存储位置**：通过方法参数传入，不存储在享元对象中
- **特点**：每次使用都不同，不能共享
- **示例**：位置坐标、颜色、选中状态

```java
// 外部状态通过参数传入
public void render(int x, int y, String color) {
    // 使用内部状态 + 外部状态
}
```

### 3. 判断标准

如何区分内部状态和外部状态？问自己三个问题：

1. **这个属性会随使用场景变化吗？**
   - 不变 → 内部状态
   - 变化 → 外部状态

2. **这个属性是对象的本质特征吗？**
   - 是 → 内部状态
   - 否 → 外部状态

3. **用这个属性作为缓存key，会产生多少对象？**
   - 数量可控（几十、几百） → 内部状态
   - 数量爆炸（成千上万） → 外部状态

## 关键设计

### 1. 享元对象（只存内部状态）

```java
public class CharacterFlyweight {
    // ✅ 内部状态
    private final char character;
    private final String font;
    private final int fontSize;
    
    // ❌ 不存储外部状态
    // private int x, y;  // 错误！
    
    // 外部状态通过参数传入
    public void render(int x, int y, String color) {
        System.out.printf("渲染字符 '%c' 在位置(%d,%d)", 
                         character, x, y);
    }
}
```

### 2. 享元工厂（管理缓存池）

```java
public class CharacterFactory {
    // 关键：缓存池
    private Map<String, CharacterFlyweight> flyweights = new HashMap<>();
    
    public CharacterFlyweight getCharacter(char c, String font, int size) {
        // 关键：用内部状态构造key
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

## 内存布局

```
享元工厂:
┌─────────────────────┐
│ Map<String, Flyweight> │
│ "A_Arial_12" → [对象A] │  ← 只有1个对象
│ "B_Arial_12" → [对象B] │
└─────────────────────┘

文档对象:
┌──────────────────────┐
│ CharacterContext[0]: │
│   flyweight → [对象A] │  ← 引用享元
│   x=10, y=20         │  ← 外部状态
│   color=RED          │
├──────────────────────┤
│ CharacterContext[1]: │
│   flyweight → [对象A] │  ← 复用同一个享元！
│   x=15, y=20         │  ← 不同的外部状态
│   color=BLUE         │
└──────────────────────┘
```

**关键**：多个 Context 引用同一个 Flyweight，但各自保存不同的外部状态。

## 运行结果解读

```
🎯 享元模式示例 - 文档编辑器字符渲染系统

示例1：输入 'HELLO'
✨ 创建新享元: H_Arial_12
✨ 创建新享元: E_Arial_12
✨ 创建新享元: L_Arial_12
♻️  复用享元: L_Arial_12  <- 第二个'L'复用了！
✨ 创建新享元: O_Arial_12

示例3：输入 1000 个字符
缓存池大小: 26 个享元对象  <- 只创建了26个对象
复用次数: 974
复用率: 97.40%             <- 97%的对象都被复用了！
内存优化: 减少了 974 个对象的创建
```

## 效果对比

### 不使用享元模式
```
1000 个字符 = 1000 个完整对象
每个对象：字符 + 字体 + 字号 + 位置 + 颜色
内存占用：巨大
```

### 使用享元模式
```
1000 个字符 = 26 个享元 + 1000 个轻量 Context
享元：字符 + 字体 + 字号（共享）
Context：享元引用 + 位置 + 颜色（独立）
内存占用：减少 97%+
```

## 与原型模式的区别

| 模式 | 本质 | 对象关系 | 形象比喻 |
|------|------|---------|---------|
| **享元** | 共享一个 | 多个引用指向同一对象 | 图书馆（一本书被多人借） |
| **原型** | 克隆多个 | 每次产生独立副本 | 复印机（每次复印新副本） |

```java
// 享元模式：共享
Flyweight f1 = factory.get("A");
Flyweight f2 = factory.get("A");
f1 == f2  // true - 同一个对象

// 原型模式：克隆
Prototype p1 = original.clone();
Prototype p2 = original.clone();
p1 == p2  // false - 不同对象
```

## 适用场景

1. ✅ 大量细粒度对象
2. ✅ 内存敏感
3. ✅ 状态可分离（内部/外部）
4. ✅ 外部状态可传入

## 经典应用

- **Java String 常量池**
- **Integer 缓存池**（-128 到 127）
- **游戏开发**：子弹、粒子效果
- **GUI 系统**：字体、图标

## 总结

享元模式的核心是**存储位置分离**：

1. **内部状态**：存在享元对象内部（final），放入缓存池共享
2. **外部状态**：通过方法参数传入，存在 Context 中

一个池子管不变的（缓存池），一个数组管变化的（Context数组），这就是享元模式的精髓！
