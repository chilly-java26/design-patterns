# 访问者模式 (Visitor Pattern)

## 一句话本质
**将数据结构与数据操作分离，通过双重分派让你可以在不修改数据结构类的情况下，为其添加新的操作方法。**

## 核心概念

### 什么是访问者模式？
访问者模式允许你定义作用于对象结构中各元素的操作，而不改变元素的类。它通过**双重分派**机制实现：
1. 第一次分派：`element.accept(visitor)` - 根据元素类型分派
2. 第二次分派：`visitor.visit(this)` - 根据访问者类型分派

### 关键角色
1. **Visitor（访问者接口）**: 定义对每种元素的访问操作
2. **ConcreteVisitor（具体访问者）**: 实现具体的访问操作
3. **Element（元素接口）**: 定义 `accept(Visitor)` 方法
4. **ConcreteElement（具体元素）**: 实现 `accept` 方法，调用 `visitor.visit(this)`

## 项目结构

```
20-visitor/
├── src/main/java/com/designpatterns/visitor/
│   ├── EmployeeVisitor.java              # 访问者接口
│   ├── Employee.java                      # 元素接口
│   ├── Engineer.java                      # 具体元素：工程师
│   ├── Manager.java                       # 具体元素：经理
│   ├── Intern.java                        # 具体元素：实习生
│   ├── SalaryCalculator.java            # 具体访问者：薪资计算
│   ├── BenefitsCalculator.java          # 具体访问者：福利计算
│   ├── PerformanceReportGenerator.java  # 具体访问者：绩效报告
│   └── VisitorDemo.java                 # 演示程序
└── pom.xml
```

## 业务场景

**公司员工管理系统**：
- **员工类型**（数据结构）：工程师、经理、实习生
- **操作**（访问者）：薪资计算、福利计算、绩效报告生成

每种员工有不同的属性和计算规则，通过访问者模式可以方便地添加新操作，而不修改员工类。

## 核心实现

### 1. 元素接口和实现

```java
// 元素接口
public interface Employee {
    void accept(EmployeeVisitor visitor);  // 核心方法
}

// 具体元素
public class Engineer implements Employee {
    @Override
    public void accept(EmployeeVisitor visitor) {
        visitor.visit(this);  // 双重分派的关键
    }
}
```

### 2. 访问者接口和实现

```java
// 访问者接口
public interface EmployeeVisitor {
    void visit(Engineer engineer);
    void visit(Manager manager);
    void visit(Intern intern);
}

// 具体访问者
public class SalaryCalculator implements EmployeeVisitor {
    @Override
    public void visit(Engineer engineer) {
        // 计算工程师薪资的特定逻辑
    }
}
```

### 3. 使用方式

```java
Employee employee = new Engineer("张三", 15000, 12000);
EmployeeVisitor visitor = new SalaryCalculator();
employee.accept(visitor);  // 触发双重分派
```

## 运行示例

```bash
cd 20-visitor
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.visitor.VisitorDemo"
```

### 输出示例

```
【访问者1: 薪资计算器】
=========================================
工程师 张三: 基本工资=15000.00, 代码行数=12000, 代码奖金=1200.00, 总计=16200.00
经理 王经理: 基本工资=25000.00, 团队规模=8, 管理奖金=4000.00, 总计=29000.00
实习生 小明: 基本工资=3000.00, 工作天数=28, 实际工资=2800.00

公司总薪资支出: 48000.00 元

【访问者2: 福利计算器】
=========================================
工程师 张三: 五险一金=2250.00, 餐补=500, 交通=300, 总福利=3050.00
经理 王经理: 五险一金=3750.00, 餐补=500, 交通=300, 通讯=500, 管理津贴=1000, 总福利=6050.00
实习生 小明: 餐补=300, 总福利=300.00

公司总福利支出: 9400.00 元
```

## 优缺点

### 优点
1. **符合开闭原则**: 添加新操作容易（新建访问者），不需修改元素类
2. **单一职责**: 操作逻辑集中在访问者中，元素类只负责数据
3. **灵活性**: 同一数据结构可以有多种不同的操作
4. **易于聚合操作**: 访问者可以累积状态（如总薪资）

### 缺点
1. **添加新元素困难**: 新增元素类型需修改所有访问者接口
2. **破坏封装**: 访问者需要访问元素的内部数据
3. **依赖具体类**: 访问者接口依赖所有具体元素类型

## 适用场景

1. **对象结构稳定**，但操作经常变化
2. 需要对对象结构中的元素进行**多种不相关的操作**
3. 对象结构包含多种类型的对象，需要**根据类型执行不同操作**

### 实际应用
- **编译器**: AST（抽象语法树）遍历 - 类型检查、代码生成、优化
- **文档处理**: 导出为不同格式（PDF、HTML、Markdown）
- **税务系统**: 不同类型收入的税收计算
- **游戏开发**: 游戏实体的渲染、碰撞检测、AI计算

## 与其他模式的对比

| 模式 | 特点 | 适用场景 |
|------|------|---------|
| **访问者模式** | 外部操作，双重分派 | 结构稳定，操作多变 |
| **策略模式** | 封装算法，单一分派 | 算法可互换 |
| **命令模式** | 封装请求为对象 | 请求参数化、队列、日志 |

## 关键要点

1. ✅ **数据结构不持有访问者** - 只是临时接受访问
2. ✅ **双重分派是核心** - `accept()` + `visit()` 配合
3. ✅ **扩展操作不改元素** - 添加访问者即可
4. ⚠️ **不要频繁增加元素类型** - 会影响所有访问者

## 扩展练习

尝试添加一个新的访问者 `TaxCalculator`（税务计算器），为不同类型员工计算个人所得税：
- 工程师：20% 税率
- 经理：25% 税率
- 实习生：免税

只需创建新的访问者类，不需要修改任何员工类！
