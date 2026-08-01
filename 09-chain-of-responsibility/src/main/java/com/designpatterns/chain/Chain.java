package com.designpatterns.chain;

/**
 * 责任链接口
 * 类似 Servlet FilterChain
 */
public interface Chain {
    /**
     * 将请求传递给下一个处理者
     */
    void next(LeaveRequest request);
}
