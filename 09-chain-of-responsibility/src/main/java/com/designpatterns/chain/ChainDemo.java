package com.designpatterns.chain;

/**
 * 责任链模式演示
 * 场景：请假审批流程
 */
public class ChainDemo {
    
    public static void main(String[] args) {
        // 构建审批责任链
        ApprovalChain chain = new ApprovalChain()
                .addApprover(new Leader())
                .addApprover(new Manager())
                .addApprover(new Director())
                .addApprover(new Boss());
        
        // 测试不同天数的请假申请
        chain.process(new LeaveRequest("张三", 1));   // 组长批准
        chain.process(new LeaveRequest("李四", 3));   // 经理批准
        chain.process(new LeaveRequest("王五", 5));   // 总监批准
        chain.process(new LeaveRequest("赵六", 15));  // 老板批准
    }
}
