package com.designpatterns.chain;

/**
 * 抽象审批者
 * 处理请求，可以选择调用 chain.next() 继续传递
 */
public abstract class Approver {
    
    protected String name;
    
    public Approver(String name) {
        this.name = name;
    }
    
    /**
     * 处理请求
     * @param request 请假申请
     * @param chain 责任链，用于传递请求
     */
    public abstract void handle(LeaveRequest request, Chain chain);
    
    public String getName() {
        return name;
    }
}
