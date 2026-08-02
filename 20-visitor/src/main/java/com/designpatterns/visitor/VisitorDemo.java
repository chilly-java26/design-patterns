package com.designpatterns.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 访问者模式演示
 * 场景：公司员工薪资和福利系统
 */
public class VisitorDemo {
    public static void main(String[] args) {
        // 创建公司员工列表
        List<Employee> employees = new ArrayList<>();
        employees.add(new Engineer("张三", 15000, 12000));
        employees.add(new Engineer("李四", 18000, 8000));
        employees.add(new Manager("王经理", 25000, 8));
        employees.add(new Manager("刘经理", 30000, 12));
        employees.add(new Intern("小明", 3000, 28));
        employees.add(new Intern("小红", 3000, 22));
        
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║       访问者模式 - 员工管理系统演示       ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        // 访问者1: 计算薪资
        System.out.println("【访问者1: 薪资计算器】");
        System.out.println("=========================================");
        SalaryCalculator salaryCalculator = new SalaryCalculator();
        for (Employee employee : employees) {
            employee.accept(salaryCalculator);  // 核心：双重分派
        }
        System.out.printf("\n公司总薪资支出: %.2f 元\n", salaryCalculator.getTotalSalary());
        
        System.out.println("\n\n");
        
        // 访问者2: 计算福利
        System.out.println("【访问者2: 福利计算器】");
        System.out.println("=========================================");
        BenefitsCalculator benefitsCalculator = new BenefitsCalculator();
        for (Employee employee : employees) {
            employee.accept(benefitsCalculator);  // 同样的数据，不同的操作
        }
        System.out.printf("\n公司总福利支出: %.2f 元\n", benefitsCalculator.getTotalBenefits());
        
        System.out.println("\n\n");
        
        // 访问者3: 生成绩效报告
        System.out.println("【访问者3: 绩效报告生成器】");
        System.out.println("=========================================");
        PerformanceReportGenerator reportGenerator = new PerformanceReportGenerator();
        for (Employee employee : employees) {
            employee.accept(reportGenerator);  // 又一个新操作，无需修改员工类
        }
        
        System.out.println("\n\n");
        
        // 演示双重分派的过程
        demonstrateDoubleDispatch();
    }
    
    /**
     * 演示双重分派的执行过程
     */
    private static void demonstrateDoubleDispatch() {
        System.out.println("【双重分派执行过程演示】");
        System.out.println("=========================================");
        
        Employee engineer = new Engineer("测试工程师", 15000, 10000);
        EmployeeVisitor visitor = new SalaryCalculator();
        
        System.out.println("执行: engineer.accept(visitor)");
        System.out.println("  ↓");
        System.out.println("第一次分派: 根据 engineer 的实际类型 (Engineer)");
        System.out.println("  调用 Engineer.accept(visitor)");
        System.out.println("  ↓");
        System.out.println("第二次分派: 在 Engineer.accept() 中调用");
        System.out.println("  visitor.visit(this) → 传入 Engineer 对象");
        System.out.println("  ↓");
        System.out.println("最终执行: SalaryCalculator.visit(Engineer)");
        System.out.println("  ↓");
        System.out.println("结果:");
        
        engineer.accept(visitor);
    }
}
