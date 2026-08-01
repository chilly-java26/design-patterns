package com.designpatterns.chain;

/**
 * 请假申请
 */
public class LeaveRequest {
    
    private String name;  // 员工姓名
    private int days;     // 请假天数
    
    public LeaveRequest(String name, int days) {
        this.name = name;
        this.days = days;
    }
    
    public String getName() {
        return name;
    }
    
    public int getDays() {
        return days;
    }
}
