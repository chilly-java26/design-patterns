# 备忘录模式（Memento Pattern）

## 一句话解释
**在不破坏封装性的前提下，捕获并保存对象的内部状态，以便后续可以恢复到该状态。**

## 核心角色

### 1. 备忘录（Memento）- GameSnapshot
- 存储状态快照
- 不可变对象，只提供读取接口
- 示例：`GameSnapshot` 存储角色的 level、hp、gold、location

### 2. 发起人（Originator）- GameCharacter  
- 创建快照：`snapshot()` 方法
- 恢复状态：`restore(GameSnapshot)` 方法
- 包含业务逻辑：战斗、升级、移动等

### 3. 管理者（Caretaker）- SaveSystem
- 管理多个存档槽位
- 提供保存、读取、删除功能
- 不能修改快照内容

## 职责分离

| 角色 | 管什么 | 通过什么实现 |
|-----|-------|------------|
| 发起人 | 创建和恢复 | `snapshot()` 和 `restore()` 方法 |
| 管理者 | 保存和提供 | `Map<String, GameSnapshot>` 容器 |

## 优点

1. **保护封装性**：角色内部状态不会暴露，外部只能拿到不透明的快照对象
2. **职责分离**：角色管业务逻辑，存档系统管存档管理，互不干扰
3. **状态恢复简单**：直接 restore 即可，不用手动记录每个属性的历史值
4. **易于扩展**：可以轻松添加云存档、自动存档、存档数量限制等功能

## 应用场景

- 游戏存档/读档
- 文本编辑器的撤销/重做（Undo/Redo）
- 数据库事务回滚
- 浏览器的前进/后退
- 配置文件的版本管理

## 运行示例

```bash
cd 22-memento
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.memento.MementoDemo"
```

## 示例输出

程序演示了：
1. 在不同地点保存进度（新手村、森林、BOSS前）
2. 挑战BOSS失败后读档重来
3. 查看所有存档列表
4. 回到之前任意存档点
5. 删除指定存档

展示了备忘录模式如何优雅地实现游戏存档系统。
