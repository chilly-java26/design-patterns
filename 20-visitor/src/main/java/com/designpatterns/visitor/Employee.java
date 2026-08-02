package com.designpatterns.visitor;

/**
 * 员工接口 - 数据结构的抽象
 */
public interface Employee {
    /**
     * 接受访问者的访问
     */
    void accept(EmployeeVisitor visitor);
    
    String getName();
    double getBaseSalary();
}
