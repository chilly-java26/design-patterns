package com.designpatterns.visitor;

/**
 * 工程师 - 具体元素
 */
public class Engineer implements Employee {
    private String name;
    private double baseSalary;
    private int codeLines;  // 代码行数
    
    public Engineer(String name, double baseSalary, int codeLines) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.codeLines = codeLines;
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
    
    public int getCodeLines() {
        return codeLines;
    }
}
