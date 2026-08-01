package com.designpatterns.chain;

/**
 * 老板 - 能批准任意天数的假
 */
public class Boss extends Approver {
    
    public Boss() {
        super("老板");
    }
    
    @Override
    public void handle(LeaveRequest request, Chain chain) {
        System.out.println("[" + name + "] 前置处理：开始审批");
        
        System.out.println("[" + name + "] 批准了" + request.getName() + "的" + request.getDays() + "天假期");
        // 老板是最终决策者，不需要继续传递
        
        System.out.println("[" + name + "] 后置处理：审批完成");
    }
}
