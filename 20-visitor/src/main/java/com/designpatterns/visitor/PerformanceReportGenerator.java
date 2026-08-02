package com.designpatterns.visitor;

/**
 * 绩效报告生成访问者 - 具体访问者
 * 为不同类型员工生成绩效报告
 */
public class PerformanceReportGenerator implements EmployeeVisitor {
    
    @Override
    public void visit(Engineer engineer) {
        System.out.println("=== 工程师绩效报告 ===");
        System.out.println("姓名: " + engineer.getName());
        System.out.println("职位: 软件工程师");
        System.out.println("代码贡献: " + engineer.getCodeLines() + " 行");
        
        String rating;
        if (engineer.getCodeLines() > 10000) {
            rating = "优秀 (A)";
        } else if (engineer.getCodeLines() > 5000) {
            rating = "良好 (B)";
        } else {
            rating = "合格 (C)";
        }
        System.out.println("绩效评级: " + rating);
        System.out.println("------------------------");
    }
    
    @Override
    public void visit(Manager manager) {
        System.out.println("=== 经理绩效报告 ===");
        System.out.println("姓名: " + manager.getName());
        System.out.println("职位: 项目经理");
        System.out.println("团队规模: " + manager.getTeamSize() + " 人");
        
        String rating;
        if (manager.getTeamSize() > 10) {
            rating = "优秀 (A) - 大团队管理";
        } else if (manager.getTeamSize() > 5) {
            rating = "良好 (B) - 中团队管理";
        } else {
            rating = "合格 (C) - 小团队管理";
        }
        System.out.println("绩效评级: " + rating);
        System.out.println("------------------------");
    }
    
    @Override
    public void visit(Intern intern) {
        System.out.println("=== 实习生绩效报告 ===");
        System.out.println("姓名: " + intern.getName());
        System.out.println("职位: 实习生");
        System.out.println("出勤天数: " + intern.getWorkDays() + " 天");
        
        String rating;
        if (intern.getWorkDays() >= 25) {
            rating = "优秀 (A) - 全勤";
        } else if (intern.getWorkDays() >= 20) {
            rating = "良好 (B)";
        } else {
            rating = "需改进 (C)";
        }
        System.out.println("绩效评级: " + rating);
        System.out.println("------------------------");
    }
}
