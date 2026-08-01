package com.designpatterns.chain.practice;

// 审批请求
public class ApprovalRequest {
    private String name;
    private String description;
    private Integer department;

    public ApprovalRequest(String name, String description, Integer department) {
        this.name = name;
        this.description = description;
        this.department = department;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Integer getDepartment() {
        return department;
    }
}
