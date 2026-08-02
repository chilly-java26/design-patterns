package com.designpatterns.chain.http;

/**
 * 认证过滤器
 * 检查请求是否包含有效的认证信息，并在Context中保存用户信息
 */
public class AuthenticationFilter extends Filter {
    
    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterContext context) {
        System.out.println("  [认证过滤器] 检查认证信息...");
        
        String token = request.getHeader("Authorization");
        
        if (token == null || token.isEmpty()) {
            // 认证失败，中断责任链
            System.out.println("  [认证过滤器] ❌ 认证失败：缺少 Authorization header");
            response.setStatus(401, "Unauthorized");
            response.setBody("{\"error\": \"Missing authorization token\"}");
            return;  // 不调用 invokeNext()，责任链中断
        }
        
        if (!token.equals("Bearer valid-token-123")) {
            // Token无效，中断责任链
            System.out.println("  [认证过滤器] ❌ 认证失败：无效的token");
            response.setStatus(401, "Unauthorized");
            response.setBody("{\"error\": \"Invalid token\"}");
            return;  // 责任链中断
        }
        
        System.out.println("  [认证过滤器] ✓ 认证通过");
        
        // 认证成功，解析用户信息并保存到Context
        // 模拟从token中解析出用户信息
        User user = new User("user123", "张三", "user");
        context.setAttribute("current.user", user);
        context.setAttribute("auth.success", true);
        
        System.out.println("  [认证过滤器] 用户信息已保存到Context: " + user.getName());
        
        // 认证通过，调用下一个过滤器
        invokeNext(request, response, context);
    }
    
    /**
     * 用户信息类（内部类）
     */
    public static class User {
        private String id;
        private String name;
        private String role;
        
        public User(String id, String name, String role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
        public String getRole() { return role; }
        
        @Override
        public String toString() {
            return "User{id='" + id + "', name='" + name + "', role='" + role + "'}";
        }
    }
}
