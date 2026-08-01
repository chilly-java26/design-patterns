package com.designpatterns.chain;

/**
 * 组长 - 能批准1天以内的假
 */
public class Leader extends Approver {
    
    public Leader() {
        super("组长");
    }
    
    @Override
    public void handle(LeaveRequest request, Chain chain) {
        System.out.println("[" + name + "] 前置处理：开始审批");
        
        if (request.getDays() <= 1) {
            System.out.println("[" + name + "] 批准了" + request.getName() + "的" + request.getDays() + "天假期");
        } else {
            System.out.println("[" + name + "] 权限不够，转交上级处理");
            chain.next(request);  // 继续传递
        }
        
        System.out.println("[" + name + "] 后置处理：审批完成");
    }
}
