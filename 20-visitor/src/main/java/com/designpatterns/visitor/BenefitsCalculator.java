package com.designpatterns.visitor;

/**
 * 福利计算访问者 - 具体访问者
 * 根据不同员工类型计算福利
 */
public class BenefitsCalculator implements EmployeeVisitor {
    private double totalBenefits = 0;
    
    @Override
    public void visit(Engineer engineer) {
        // 工程师：五险一金 + 餐补 + 交通补助
        double benefits = engineer.getBaseSalary() * 0.15  // 五险一金
                        + 500  // 餐补
                        + 300; // 交通补助
        
        System.out.printf("工程师 %s: 五险一金=%.2f, 餐补=500, 交通=300, 总福利=%.2f%n",
                engineer.getName(), engineer.getBaseSalary() * 0.15, benefits);
        
        totalBenefits += benefits;
    }
    
    @Override
    public void visit(Manager manager) {
        // 经理：五险一金 + 餐补 + 交通补助 + 通讯补助 + 管理津贴
        double benefits = manager.getBaseSalary() * 0.15  // 五险一金
                        + 500  // 餐补
                        + 300  // 交通补助
                        + 500  // 通讯补助
                        + 1000; // 管理津贴
        
        System.out.printf("经理 %s: 五险一金=%.2f, 餐补=500, 交通=300, 通讯=500, 管理津贴=1000, 总福利=%.2f%n",
                manager.getName(), manager.getBaseSalary() * 0.15, benefits);
        
        totalBenefits += benefits;
    }
    
    @Override
    public void visit(Intern intern) {
        // 实习生：仅餐补
        double benefits = 300;  // 餐补
        
        System.out.printf("实习生 %s: 餐补=300, 总福利=%.2f%n",
                intern.getName(), benefits);
        
        totalBenefits += benefits;
    }
    
    public double getTotalBenefits() {
        return totalBenefits;
    }
}
