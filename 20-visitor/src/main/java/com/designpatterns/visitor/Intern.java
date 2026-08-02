package com.designpatterns.visitor;

/**
 * 实习生 - 具体元素
 */
public class Intern implements Employee {
    private String name;
    private double baseSalary;
    private int workDays;  // 工作天数
    
    public Intern(String name, double baseSalary, int workDays) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.workDays = workDays;
    }
    
    @Override
    public void accept(EmployeeVisitor visitor) {
        // 双重分派的关键：把自己传给访问者
        visitor.visit(this);
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public double getBaseSalary() {
        return baseSalary;
    }
    
    public int getWorkDays() {
        return workDays;
    }
}
