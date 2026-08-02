package com.designpatterns.chain.http;

/**
 * 过滤器抽象类
 * 每个Filter持有下一个Filter的引用，形成真正的链式结构
 */
public abstract class Filter {
    
    protected Filter next;  // 持有下一个过滤器的引用
    
    /**
     * 设置下一个过滤器
     */
    public Filter setNext(Filter next) {
        this.next = next;
        return next;  // 返回next，方便链式调用
    }
    
    /**
     * 处理请求
     * @param request HTTP请求
     * @param response HTTP响应
     * @param context 过滤器上下文，用于在过滤器之间传递数据
     */
    public abstract void doFilter(HttpRequest request, HttpResponse response, FilterContext context);
    
    /**
     * 调用下一个过滤器
     * 这是责任链的核心：直接调用next.doFilter()
     */
    protected void invokeNext(HttpRequest request, HttpResponse response, FilterContext context) {
        if (next != null) {
            next.doFilter(request, response, context);
        }
    }
}
