package com.designpatterns.chain.practice;

public class BossApprover extends Approver {

    public BossApprover(String name) {
        super(name);
    }

    @Override
    public void approve(ApprovalRequest request, IChain chain) {
        System.out.println("Start approval by Boss: " + getName());

        // request will be approved by boss2
        if ("boss2".equals(getName())) {
            System.out.println("✅ Approved by Boss: " + getName());
        } else {
            System.out.println("↩️ Approval forwarded by Boss: " + getName());
            chain.next(request);
        }

        System.out.println("End approval by Boss: " + getName());
    }
}
