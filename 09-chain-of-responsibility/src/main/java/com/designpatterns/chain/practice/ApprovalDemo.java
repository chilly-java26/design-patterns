package com.designpatterns.chain.practice;

public class ApprovalDemo {
    public static void main(String[] args) {
        ApprovalRequest request = new ApprovalRequest(
                "张三",
                "请假旅行",
                1);

        ApprovalChain chain = new ApprovalChain()
                .addApprover(new BossApprover("boss1"))
                .addApprover(new BossApprover("boss2"));

        chain.startApproval(request);
    }
}
