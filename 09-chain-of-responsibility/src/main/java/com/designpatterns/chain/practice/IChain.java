package com.designpatterns.chain.practice;

// 开始审批的接口
public interface IChain {
    void next(ApprovalRequest request);
}
