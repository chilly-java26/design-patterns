package com.designpatterns.chain.practice;

// 审批者抽象类，定义审批接口
public abstract class Approver {
    private String name;

    public Approver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void approve(ApprovalRequest request, IChain chain);
}
