package com.designpatterns.chain;

/**
 * 经理 - 能批准3天以内的假
 */
public class Manager extends Approver {
    
    public Manager() {
        super("经理");
    }
    
    @Override
    public void handle(LeaveRequest request, Chain chain) {
        System.out.println("[" + name + "] 前置处理：开始审批");
        
        if (request.getDays() <= 3) {
            System.out.println("[" + name + "] 批准了" + request.getName() + "的" + request.getDays() + "天假期");
        } else {
            System.out.println("[" + name + "] 权限不够，转交上级处理");
            chain.next(request);
        }
        
        System.out.println("[" + name + "] 后置处理：审批完成");
    }
}
