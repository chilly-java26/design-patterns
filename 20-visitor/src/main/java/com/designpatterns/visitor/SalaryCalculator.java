package com.designpatterns.visitor;

/**
 * 薪资计算访问者 - 具体访问者
 * 根据不同员工类型计算薪资
 */
public class SalaryCalculator implements EmployeeVisitor {
    private double totalSalary = 0;
    
    @Override
    public void visit(Engineer engineer) {
        // 工程师：基本工资 + 代码奖金（每千行100元）
        double bonus = (engineer.getCodeLines() / 1000.0) * 100;
        double salary = engineer.getBaseSalary() + bonus;
        
        System.out.printf("工程师 %s: 基本工资=%.2f, 代码行数=%d, 代码奖金=%.2f, 总计=%.2f%n",
                engineer.getName(), engineer.getBaseSalary(), 
                engineer.getCodeLines(), bonus, salary);
        
        totalSalary += salary;
    }
    
    @Override
    public void visit(Manager manager) {
        // 经理：基本工资 + 团队管理奖金（每人500元）
        double bonus = manager.getTeamSize() * 500;
        double salary = manager.getBaseSalary() + bonus;
        
        System.out.printf("经理 %s: 基本工资=%.2f, 团队规模=%d, 管理奖金=%.2f, 总计=%.2f%n",
                manager.getName(), manager.getBaseSalary(), 
                manager.getTeamSize(), bonus, salary);
        
        totalSalary += salary;
    }
    
    @Override
    public void visit(Intern intern) {
        // 实习生：基本工资 * 实际工作天数 / 30
        double salary = intern.getBaseSalary() * intern.getWorkDays() / 30.0;
        
        System.out.printf("实习生 %s: 基本工资=%.2f, 工作天数=%d, 实际工资=%.2f%n",
                intern.getName(), intern.getBaseSalary(), 
                intern.getWorkDays(), salary);
        
        totalSalary += salary;
    }
    
    public double getTotalSalary() {
        return totalSalary;
    }
}
