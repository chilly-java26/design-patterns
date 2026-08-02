# SQL WHERE 条件解释器

## 概述

这是一个使用解释器模式实现的 SQL WHERE 条件过滤器，展示了解释器模式在**非数学场景**下的应用。

## 功能

支持以下 SQL WHERE 条件语法：

### 比较运算符
- `=` - 等于
- `!=` - 不等于
- `>` - 大于
- `<` - 小于
- `>=` - 大于等于
- `<=` - 小于等于

### 逻辑运算符
- `AND` - 逻辑与
- `OR` - 逻辑或
- `NOT` - 逻辑非

### 支持的数据类型
- 整数 (Integer)
- 字符串 (String)
- 布尔值 (Boolean)

## 核心类说明

### 1. Context（上下文）
存储变量和对应的值，类似于数据库记录。

```java
Context context = new Context();
context.set("age", 25);
context.set("status", "active");
```

### 2. BooleanExpression（抽象表达式）
所有条件表达式的接口。

```java
interface BooleanExpression {
    boolean interpret(Context context);
}
```

### 3. CompareExpression（比较表达式 - 终结符）
执行实际的比较操作。

```java
// age > 25
new CompareExpression("age", ">", 25)

// status = "active"
new CompareExpression("status", "=", "active")
```

### 4. AndExpression（AND 表达式 - 非终结符）
两个条件都满足才返回 true。

```java
// age > 25 AND status = "active"
new AndExpression(
    new CompareExpression("age", ">", 25),
    new CompareExpression("status", "=", "active")
)
```

### 5. OrExpression（OR 表达式 - 非终结符）
只要有一个条件满足就返回 true。

```java
// vip = true OR level >= 5
new OrExpression(
    new CompareExpression("vip", "=", true),
    new CompareExpression("level", ">=", 5)
)
```

### 6. NotExpression（NOT 表达式 - 非终结符）
对条件结果取反。

```java
// NOT (status = "inactive")
new NotExpression(
    new CompareExpression("status", "=", "inactive")
)
```

## 使用示例

### 示例 1：简单条件
```java
// WHERE age > 25
BooleanExpression condition = new CompareExpression("age", ">", 25);

Context context = new Context();
context.set("age", 28);

boolean result = condition.interpret(context); // true
```

### 示例 2：AND 条件
```java
// WHERE age > 20 AND status = "active"
BooleanExpression condition = new AndExpression(
    new CompareExpression("age", ">", 20),
    new CompareExpression("status", "=", "active")
);

Context context = new Context();
context.set("age", 25);
context.set("status", "active");

boolean result = condition.interpret(context); // true
```

### 示例 3：复杂嵌套条件
```java
// WHERE (age > 25 AND status = "active") OR (vip = true AND level >= 3)
BooleanExpression condition = new OrExpression(
    new AndExpression(
        new CompareExpression("age", ">", 25),
        new CompareExpression("status", "=", "active")
    ),
    new AndExpression(
        new CompareExpression("vip", "=", true),
        new CompareExpression("level", ">=", 3)
    )
);
```

### 示例 4：过滤用户列表
```java
List<User> users = getUsers();
BooleanExpression condition = new CompareExpression("age", ">", 25);

List<User> filteredUsers = new ArrayList<>();
for (User user : users) {
    Context context = user.toContext();
    if (condition.interpret(context)) {
        filteredUsers.add(user);
    }
}
```

## 表达式树结构

### 简单条件
```
WHERE age > 25

树结构：
CompareExpression("age", ">", 25)  [终结符]
```

### AND 条件
```
WHERE age > 20 AND status = "active"

树结构：
       AndExpression  [非终结符]
         /        \
CompareExpression  CompareExpression  [终结符]
 (age > 20)       (status = "active")
```

### 复杂嵌套条件
```
WHERE (age > 25 AND status = "active") OR (vip = true AND level >= 3)

树结构：
                OrExpression  [非终结符]
               /              \
        AndExpression      AndExpression  [非终结符]
         /        \          /         \
    Compare   Compare   Compare    Compare  [终结符]
   (age>25) (status) (vip=true) (level>=3)
```

## 运行演示

```bash
cd 21-interpreter
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.interpreter.sql.SqlWhereInterpreterDemo"
```

## 实际应用场景

### 1. 数据过滤引擎
在内存中对数据集合进行条件过滤，避免复杂的 SQL 查询。

```java
// 从缓存中筛选符合条件的用户
BooleanExpression vipUserCondition = new AndExpression(
    new CompareExpression("vip", "=", true),
    new CompareExpression("level", ">=", 3)
);

List<User> vipUsers = filterUsers(cachedUsers, vipUserCondition);
```

### 2. 权限控制系统
根据用户属性动态判断是否有权限。

```java
// 只有 VIP 用户或管理员才能访问
BooleanExpression accessRule = new OrExpression(
    new CompareExpression("role", "=", "admin"),
    new CompareExpression("vip", "=", true)
);

if (accessRule.interpret(user.toContext())) {
    // 允许访问
}
```

### 3. 规则引擎
根据业务规则筛选符合条件的对象。

```java
// 优惠券发放规则：年龄>18 并且 (VIP用户 或 消费金额>1000)
BooleanExpression couponRule = new AndExpression(
    new CompareExpression("age", ">", 18),
    new OrExpression(
        new CompareExpression("vip", "=", true),
        new CompareExpression("totalSpent", ">", 1000)
    )
);
```

### 4. 配置化查询
将查询条件存储为对象，可序列化、可缓存。

```java
// 保存查询条件
BooleanExpression savedQuery = buildQueryFromConfig();

// 后续复用
List<User> results = filterUsers(users, savedQuery);
```

## 优势

1. **灵活组合** - 可以动态构建任意复杂的条件
2. **易于扩展** - 添加新的运算符只需新增类
3. **类型安全** - 编译时检查，避免 SQL 注入
4. **可测试** - 每个表达式都可以独立测试
5. **可序列化** - 表达式树可以序列化存储

## 局限性

1. **不支持复杂 SQL** - 只支持 WHERE 条件，不支持 JOIN、GROUP BY 等
2. **性能开销** - 对象创建和递归调用有性能开销
3. **不适合大数据量** - 内存过滤，不适合数据库级别的大数据量查询

## 与真实 SQL 的对比

| 特性 | 解释器模式 | 真实 SQL |
|------|----------|---------|
| 执行位置 | 内存中 | 数据库中 |
| 适用数据量 | 小到中等 | 任意规模 |
| 性能 | 较低 | 高（有索引） |
| 灵活性 | 高（可编程） | 低（受限于 SQL 语法） |
| 类型安全 | 是 | 否（字符串拼接） |
| SQL 注入风险 | 无 | 有（需防范） |

## 总结

SQL WHERE 条件解释器展示了解释器模式在非数学场景下的强大应用能力。通过将 SQL 语法规则对象化，我们可以：

- ✅ 在 Java 代码中灵活构建查询条件
- ✅ 避免字符串拼接和 SQL 注入风险
- ✅ 实现类型安全的条件判断
- ✅ 轻松扩展新的运算符和逻辑

这种模式特别适合：
- **内存数据过滤**
- **规则引擎**
- **权限系统**
- **配置化查询**

记住：解释器模式不是为了替代数据库，而是为了在应用层提供灵活的规则表达和执行能力！
