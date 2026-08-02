package com.designpatterns.state.states;

import com.designpatterns.state.AbstractOrderState;

/**
 * 已取消状态（终态）
 * 所有操作都不可用（全部使用基类默认实现）
 */
public class CancelledState extends AbstractOrderState {
    
    @Override
    public String getStateName() {
        return "已取消";
    }
    
    // 所有操作都使用基类的默认实现（拒绝操作）
    // pay(), ship(), deliver(), cancel(), refund(), review()
}
