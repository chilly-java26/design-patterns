package com.designpatterns.chain.http;

/**
 * 日志过滤器
 * 记录请求和响应信息，并在Context中保存日志信息
 */
public class LoggingFilter extends Filter {
    
    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterContext context) {
        long startTime = System.currentTimeMillis();
        
        System.out.println("  [日志过滤器 - 前置] 收到请求: " + request);
        
        // 在Context中保存请求开始时间
        context.setAttribute("request.startTime", startTime);
        context.setAttribute("request.uri", request.getUri());
        
        // 调用下一个过滤器
        invokeNext(request, response, context);
        
        // 计算请求处理耗时
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("  [日志过滤器 - 后置] 响应状态: " + response.getStatusCode() + 
                          " | 耗时: " + duration + "ms");
        
        // 在Context中保存耗时信息
        context.setAttribute("request.duration", duration);
    }
}
