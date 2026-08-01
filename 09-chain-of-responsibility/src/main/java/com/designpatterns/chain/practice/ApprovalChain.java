package com.designpatterns.chain.practice;

import java.util.ArrayList;
import java.util.List;

// 审批链：允许添加审批者、开始审批流程、被审批者继续调用下一步审批
public class ApprovalChain implements IChain {
    private List<Approver> approvers = new ArrayList<Approver>();
    private int approverIndex = 0;

    public ApprovalChain addApprover(Approver approver) {
        approvers.add(approver);
        return this;
    }

    public void startApproval(ApprovalRequest request) {
        approverIndex = 0;
        next(request);
    }

    @Override
    public void next(ApprovalRequest request) {
        if (approverIndex < approvers.size()) {
            Approver approver = approvers.get(approverIndex);
            approverIndex++;
            approver.approve(request, this);
        }
    }
}
