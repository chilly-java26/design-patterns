package com.designpatterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门类 - 组合节点
 * 可以包含员工或子部门
 */
public class Department extends OrganizationComponent {
    private List<OrganizationComponent> children = new ArrayList<>();

    public Department(String name) {
        super(name);
    }

    @Override
    public void add(OrganizationComponent component) {
        children.add(component);
    }

    @Override
    public void remove(OrganizationComponent component) {
        children.remove(component);
    }

    @Override
    public void display(int indent) {
        System.out.println(getIndent(indent) + "┌─ 部门: " + name);
        // 递归显示所有子节点
        for (OrganizationComponent child : children) {
            child.display(indent + 1);
        }
    }

    @Override
    public int getEmployeeCount() {
        int count = 0;
        // 递归统计所有子节点的人数
        for (OrganizationComponent child : children) {
            count += child.getEmployeeCount();
        }
        return count;
    }

    @Override
    public double getTotalSalary() {
        double total = 0;
        // 递归累加所有子节点的薪资
        for (OrganizationComponent child : children) {
            total += child.getTotalSalary();
        }
        return total;
    }

    public List<OrganizationComponent> getChildren() {
        return children;
    }
}
