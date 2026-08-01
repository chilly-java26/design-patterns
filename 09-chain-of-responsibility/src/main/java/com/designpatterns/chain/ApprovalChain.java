package com.designpatterns.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批责任链
 * 管理审批者列表，负责按顺序传递请求
 */
public class ApprovalChain implements Chain {
    
    private List<Approver> approvers = new ArrayList<>();
    private int currentIndex = 0;
    
    /**
     * 添加审批者
     */
    public ApprovalChain addApprover(Approver approver) {
        approvers.add(approver);
        return this;  // 支持链式调用
    }
    
    @Override
    public void next(LeaveRequest request) {
        if (currentIndex < approvers.size()) {
            // 先取出当前处理者
            Approver approver = approvers.get(currentIndex);
            // 再递增索引（为下一次调用准备）
            currentIndex++;
            // 最后调用处理者的 handle 方法
            approver.handle(request, this);
        } else {
            System.out.println("所有审批者都无法处理，请求被拒绝");
        }
    }
    
    /**
     * 开始处理请求
     */
    public void process(LeaveRequest request) {
        System.out.println("\n========== 处理请假申请 ==========");
        System.out.println("申请人: " + request.getName() + ", 请假天数: " + request.getDays());
        System.out.println("开始审批流程...\n");
        
        currentIndex = 0;  // 重置索引
        next(request);     // 从第一个审批者开始
        
        System.out.println("\n========== 审批流程结束 ==========\n");
    }
}
