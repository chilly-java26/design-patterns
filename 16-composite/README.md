# 组合模式 (Composite Pattern)

## 一、模式定义

**将对象组织成树形结构，让客户端以统一的方式处理单个对象和对象集合。**

核心思想是"部分-整体"层次结构，使得叶子节点（单个对象）和组合节点（容器对象）具有一致的接口。

## 二、应用场景

本示例实现了一个**企业组织架构管理系统**，演示如何用组合模式管理公司、部门和员工。

### 核心功能
1. 统一的组织节点接口（获取名称、显示结构、统计人数、计算薪资）
2. 员工节点（叶子节点）- 不能包含子节点
3. 部门节点（组合节点）- 可以包含员工或子部门
4. 客户端无需判断节点类型，直接调用统一方法

## 三、UML类图

```
         OrganizationComponent (抽象类)
         ├── display(indent)
         ├── getEmployeeCount()
         ├── getTotalSalary()
         ├── add(component)
         └── remove(component)
                    ▲
                    │
         ┌──────────┴──────────┐
         │                     │
    Employee (叶子)        Department (组合)
    ├── position           ├── children: List
    ├── salary            ├── add()
    └── 实现所有方法        ├── remove()
                          └── 递归调用子节点
```

## 四、核心组件

### 1. OrganizationComponent（抽象组件）
- 定义统一接口
- 提供默认的 add/remove 实现（抛异常）

### 2. Employee（叶子节点）
- 不包含子节点
- 直接返回自己的数据（人数=1，薪资=自己的薪资）

### 3. Department（组合节点）
- 包含子节点列表（List<OrganizationComponent>）
- 递归调用子节点方法（统计人数、薪资、显示结构）

## 五、运行示例

```bash
cd 16-composite
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.composite.Main"
```

### 输出示例
```
============ 组合模式示例：企业组织架构管理 ============

【1】显示整个公司组织架构：
┌─ 部门: 科技有限公司
  ┌─ 部门: 技术部
    ┌─ 部门: 研发组
      ├─ 员工: 张三 | 职位: 高级工程师 | 薪资: 15000.0
      ├─ 员工: 李四 | 职位: 工程师 | 薪资: 12000.0
      ├─ 员工: 王五 | 职位: 初级工程师 | 薪资: 8000.0
    ┌─ 部门: 测试组
      ├─ 员工: 赵六 | 职位: 测试工程师 | 薪资: 10000.0
      ├─ 员工: 孙七 | 职位: 测试工程师 | 薪资: 9000.0
    ├─ 员工: 周八 | 职位: 技术总监 | 薪资: 25000.0
  ┌─ 部门: 销售部
    ├─ 员工: 吴九 | 职位: 销售经理 | 薪资: 18000.0
    ├─ 员工: 郑十 | 职位: 销售代表 | 薪资: 8000.0
    ├─ 员工: 钱十一 | 职位: 销售代表 | 薪资: 7500.0
  ├─ 员工: 刘十二 | 职位: 总经理 | 薪资: 30000.0

【2】公司总体数据统计：
公司总人数: 11 人
公司总薪资: 142500.0 元

【3】技术部数据统计：
技术部人数: 6 人
技术部薪资: 79000.0 元
```

## 六、核心优势

### 1. 简化客户端代码
```java
// 无需判断类型，统一调用
OrganizationComponent node = ...; // 可能是员工或部门
int count = node.getEmployeeCount(); // 自动处理
```

### 2. 自动递归处理
- 部门的 `getEmployeeCount()` 自动递归统计所有子节点
- 客户端无需关心递归逻辑

### 3. 灵活扩展
- 添加新类型（如"实习生"、"项目组"）
- 只需继承 `OrganizationComponent` 并实现接口
- 无需修改客户端代码

### 4. 动态调整
```java
// 运行时调整组织架构
techDept.remove(testGroup);
company.add(marketDept);
```

## 七、实现关键点

### 1. 统一接口
所有节点（员工、部门）都继承自 `OrganizationComponent`

### 2. 多态分派
```java
// Department 的实现
public int getEmployeeCount() {
    int count = 0;
    for (OrganizationComponent child : children) {
        count += child.getEmployeeCount(); // 多态调用
    }
    return count;
}
```

### 3. 叶子节点的边界处理
```java
// Employee 不支持 add/remove，继承默认实现（抛异常）
public void add(OrganizationComponent component) {
    throw new UnsupportedOperationException("员工节点不支持添加子节点");
}
```

## 八、适用场景

✅ **适合使用**
- 对象结构是树形层次（文件系统、组织架构、UI组件树）
- 需要统一对待单个对象和组合对象
- 客户端不应关心处理的是叶子还是容器

❌ **不适合使用**
- 节点类型差异大，难以抽象统一接口
- 需要严格限制某些节点只能包含特定类型子节点
- 树形结构很简单，引入模式会增加复杂度

## 九、其他典型应用

- **文件系统**：文件和文件夹
- **UI组件**：单个控件和容器（Panel、Layout）
- **菜单系统**：菜单项和子菜单
- **表达式树**：操作数和复合表达式
- **权限管理**：单个权限和权限组

## 十、与其他模式的关系

- **迭代器模式**：常与组合模式配合，用于遍历树形结构
- **访问者模式**：可以对组合结构中的元素执行操作
- **装饰器模式**：都使用递归组合，但装饰器强调增强功能，组合模式强调统一接口
