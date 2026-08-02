package com.designpatterns.chain.http;

/**
 * 最终处理器
 * 责任链的末端，执行实际的业务逻辑
 * 可以从Context中获取前面Filter保存的信息
 */
public class FinalHandler extends Filter {
    
    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterContext context) {
        System.out.println("    [最终处理器] 处理请求: " + request);
        
        // 从Context中获取用户信息
        AuthenticationFilter.User user = context.getAttribute("current.user", AuthenticationFilter.User.class);
        
        if (user != null) {
            System.out.println("    [最终处理器] 当前用户: " + user);
            
            // 根据用户信息返回个性化响应
            String responseBody = String.format(
                "{\"message\": \"Request processed successfully\", " +
                "\"user\": \"%s\", " +
                "\"userId\": \"%s\"}",
                user.getName(), user.getId()
            );
            response.setBody(responseBody);
        } else {
            response.setBody("{\"message\": \"Request processed successfully\"}");
        }
        
        // 在Context中标记请求已处理
        context.setAttribute("request.processed", true);
        
        // 最终处理器，不再调用 invokeNext()
    }
}
