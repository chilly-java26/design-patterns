package com.designpatterns.visitor;

/**
 * 访问者接口 - 定义对不同员工类型的访问操作
 */
public interface EmployeeVisitor {
    void visit(Engineer engineer);
    void visit(Manager manager);
    void visit(Intern intern);
}
