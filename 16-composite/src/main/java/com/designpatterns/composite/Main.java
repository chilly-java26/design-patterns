package com.designpatterns.composite;

/**
 * 组合模式演示 - 企业组织架构管理系统
 * 
 * 核心思想：
 * 1. 将对象组织成树形结构（部门-员工）
 * 2. 统一处理单个对象（员工）和对象集合（部门）
 * 3. 客户端无需判断节点类型，直接调用统一接口
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("============ 组合模式示例：企业组织架构管理 ============\n");

        // 创建公司总部
        Department company = new Department("科技有限公司");

        // 创建技术部
        Department techDept = new Department("技术部");
        Department devGroup = new Department("研发组");
        devGroup.add(new Employee("张三", "高级工程师", 15000));
        devGroup.add(new Employee("李四", "工程师", 12000));
        devGroup.add(new Employee("王五", "初级工程师", 8000));

        Department testGroup = new Department("测试组");
        testGroup.add(new Employee("赵六", "测试工程师", 10000));
        testGroup.add(new Employee("孙七", "测试工程师", 9000));

        techDept.add(devGroup);
        techDept.add(testGroup);
        techDept.add(new Employee("周八", "技术总监", 25000));

        // 创建销售部
        Department salesDept = new Department("销售部");
        salesDept.add(new Employee("吴九", "销售经理", 18000));
        salesDept.add(new Employee("郑十", "销售代表", 8000));
        salesDept.add(new Employee("钱十一", "销售代表", 7500));

        // 组装公司结构
        company.add(techDept);
        company.add(salesDept);
        company.add(new Employee("刘十二", "总经理", 30000));

        // ============ 演示统一操作 ============
        
        // 1. 显示整个公司的组织架构
        System.out.println("【1】显示整个公司组织架构：");
        company.display(0);

        // 2. 统计公司总人数和总薪资（自动递归）
        System.out.println("\n【2】公司总体数据统计：");
        System.out.println("公司总人数: " + company.getEmployeeCount() + " 人");
        System.out.println("公司总薪资: " + company.getTotalSalary() + " 元");

        // 3. 统计技术部的数据（无需判断是部门还是员工）
        System.out.println("\n【3】技术部数据统计：");
        System.out.println("技术部人数: " + techDept.getEmployeeCount() + " 人");
        System.out.println("技术部薪资: " + techDept.getTotalSalary() + " 元");

        // 4. 统计研发组的数据（同样的方式）
        System.out.println("\n【4】研发组数据统计：");
        System.out.println("研发组人数: " + devGroup.getEmployeeCount() + " 人");
        System.out.println("研发组薪资: " + devGroup.getTotalSalary() + " 元");

        // 5. 动态调整组织架构 - 从技术部移除测试组
        System.out.println("\n【5】组织架构调整：移除测试组");
        techDept.remove(testGroup);
        System.out.println("调整后技术部人数: " + techDept.getEmployeeCount() + " 人");
        System.out.println("调整后技术部薪资: " + techDept.getTotalSalary() + " 元");

        // 6. 添加新部门
        System.out.println("\n【6】添加市场部：");
        Department marketDept = new Department("市场部");
        marketDept.add(new Employee("陈十三", "市场经理", 16000));
        marketDept.add(new Employee("林十四", "市场专员", 7000));
        company.add(marketDept);

        System.out.println("\n最终公司组织架构：");
        company.display(0);
        System.out.println("\n公司最终人数: " + company.getEmployeeCount() + " 人");
        System.out.println("公司最终薪资: " + company.getTotalSalary() + " 元");

        // ============ 核心优势展示 ============
        System.out.println("\n============ 组合模式核心优势 ============");
        System.out.println("✓ 客户端无需判断节点类型（员工/部门）");
        System.out.println("✓ 统一调用 getEmployeeCount() 和 getTotalSalary()");
        System.out.println("✓ 自动递归处理树形结构");
        System.out.println("✓ 灵活添加/移除节点");
        System.out.println("✓ 易于扩展新的节点类型");
    }
}
