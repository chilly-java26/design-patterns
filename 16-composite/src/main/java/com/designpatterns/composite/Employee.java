package com.designpatterns.composite;

/**
 * 员工类 - 叶子节点
 * 不能包含子节点
 */
public class Employee extends OrganizationComponent {
    private String position;
    private double salary;

    public Employee(String name, String position, double salary) {
        super(name);
        this.position = position;
        this.salary = salary;
    }

    @Override
    public void display(int indent) {
        System.out.println(getIndent(indent) + "├─ 员工: " + name + 
                          " | 职位: " + position + 
                          " | 薪资: " + salary);
    }

    @Override
    public int getEmployeeCount() {
        return 1;
    }

    @Override
    public double getTotalSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }
}
