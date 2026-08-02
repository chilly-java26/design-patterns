package com.designpatterns.visitor;

/**
 * 经理 - 具体元素
 */
public class Manager implements Employee {
    private String name;
    private double baseSalary;
    private int teamSize;  // 团队规模
    
    public Manager(String name, double baseSalary, int teamSize) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.teamSize = teamSize;
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
    
    public int getTeamSize() {
        return teamSize;
    }
}
