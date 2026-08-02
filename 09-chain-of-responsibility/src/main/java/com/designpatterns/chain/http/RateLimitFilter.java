package com.designpatterns.chain.http;

/**
 * 限流过滤器
 * 防止请求过于频繁
 */
public class RateLimitFilter extends Filter {
    
    private int requestCount = 0;
    private final int maxRequests = 3;
    
    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterContext context) {
        requestCount++;
        System.out.println("  [限流过滤器] 当前请求数: " + requestCount + "/" + maxRequests);
        
        if (requestCount > maxRequests) {
            // 超过限流阈值，中断责任链
            System.out.println("  [限流过滤器] ❌ 请求过于频繁，被限流");
            response.setStatus(429, "Too Many Requests");
            response.setBody("{\"error\": \"Rate limit exceeded\"}");
            
            // 在Context中记录限流信息
            context.setAttribute("rateLimit.exceeded", true);
            context.setAttribute("rateLimit.count", requestCount);
            return;  // 责任链中断
        }
        
        System.out.println("  [限流过滤器] ✓ 限流检查通过");
        
        // 在Context中保存当前请求计数
        context.setAttribute("rateLimit.count", requestCount);
        
        // 限流检查通过，调用下一个过滤器
        invokeNext(request, response, context);
    }
}
