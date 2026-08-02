package com.designpatterns.chain.http;

/**
 * 授权过滤器
 * 检查用户是否有权限访问资源
 * 从Context中获取认证过滤器保存的用户信息
 */
public class AuthorizationFilter extends Filter {
    
    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterContext context) {
        System.out.println("  [授权过滤器] 检查访问权限...");
        
        String uri = request.getUri();
        
        // 从Context中获取当前用户信息（由AuthenticationFilter保存）
        AuthenticationFilter.User user = context.getAttribute("current.user", AuthenticationFilter.User.class);
        
        if (user == null) {
            System.out.println("  [授权过滤器] ❌ Context中没有用户信息");
            response.setStatus(401, "Unauthorized");
            response.setBody("{\"error\": \"User not authenticated\"}");
            return;
        }
        
        // 模拟权限检查：/admin/* 需要管理员权限
        if (uri.startsWith("/admin/")) {
            String role = request.getHeader("X-User-Role");
            
            if (!"admin".equals(role)) {
                // 权限不足，中断责任链
                System.out.println("  [授权过滤器] ❌ 权限不足：用户 " + user.getName() + 
                                 " 角色为 " + user.getRole() + "，需要admin权限");
                response.setStatus(403, "Forbidden");
                response.setBody("{\"error\": \"Access denied\"}");
                
                // 在Context中记录权限检查失败
                context.setAttribute("authz.failed", true);
                return;  // 责任链中断
            }
        }
        
        System.out.println("  [授权过滤器] ✓ 权限验证通过，用户: " + user.getName());
        
        // 在Context中记录权限检查成功
        context.setAttribute("authz.success", true);
        
        // 权限检查通过，调用下一个过滤器
        invokeNext(request, response, context);
    }
}
