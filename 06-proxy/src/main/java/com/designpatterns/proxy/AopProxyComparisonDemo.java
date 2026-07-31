package com.designpatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 模拟 Spring AOP 的代理选择机制
 * 演示如何根据目标对象自动选择代理方式
 */
public class AopProxyComparisonDemo {
    public static void main(String[] args) {
        System.out.println("=== 模拟 Spring AOP 代理选择 ===\n");
        
        // ========== 场景1：有接口 → JDK 动态代理 ==========
        System.out.println("【场景1】目标类实现了接口 → 使用 JDK 动态代理\n");
        
        UserService userServiceImpl = new UserServiceImpl();
        
        // 使用 JDK 动态代理
        UserService proxy1 = (UserService) Proxy.newProxyInstance(
            UserService.class.getClassLoader(),
            new Class<?>[]{UserService.class},
            new AopInvocationHandler(userServiceImpl)
        );
        
        System.out.println("代理对象类型: " + proxy1.getClass().getName());
        System.out.println("是否是接口的实例: " + (proxy1 instanceof UserService));
        System.out.println("能否强转为实现类: " + canCastTo(proxy1, UserServiceImpl.class));
        System.out.println();
        
        System.out.println("调用方法:");
        proxy1.addUser();
        System.out.println();
        
        // ========== 场景2：无接口 → CGLIB 代理（这里用说明） ==========
        System.out.println("\n【场景2】目标类没有实现接口 → 使用 CGLIB 代理\n");
        
        System.out.println("示例代码（需要 CGLIB 库）：");
        System.out.println("```java");
        System.out.println("Enhancer enhancer = new Enhancer();");
        System.out.println("enhancer.setSuperclass(UserManager.class);");
        System.out.println("enhancer.setCallback(new MethodInterceptor() {");
        System.out.println("    public Object intercept(Object obj, Method method,");
        System.out.println("                           Object[] args, MethodProxy proxy) {");
        System.out.println("        System.out.println(\"[AOP] 方法调用: \" + method.getName());");
        System.out.println("        return proxy.invokeSuper(obj, args);");
        System.out.println("    }");
        System.out.println("});");
        System.out.println("UserManager proxy = (UserManager) enhancer.create();");
        System.out.println("```");
        System.out.println();
        
        System.out.println("代理对象类型: UserManager$$EnhancerByCGLIB$$xxx");
        System.out.println("是否是目标类的子类: true");
        System.out.println("可以强转为目标类: true");
        
        System.out.println("\n【对比总结】\n");
        System.out.println("┌────────────────┬──────────────────┬──────────────────┐");
        System.out.println("│    特性        │  JDK动态代理     │  CGLIB代理       │");
        System.out.println("├────────────────┼──────────────────┼──────────────────┤");
        System.out.println("│ 目标类有接口   │ 是               │ 否或可选         │");
        System.out.println("│ 代理对象类型   │ 接口的实现类     │ 目标类的子类     │");
        System.out.println("│ 注入类型限制   │ 必须用接口注入   │ 可用类注入       │");
        System.out.println("│ final方法      │ 不影响           │ 无法代理         │");
        System.out.println("│ 创建速度       │ 快               │ 慢               │");
        System.out.println("│ 调用速度       │ 慢(反射)         │ 快(FastClass)    │");
        System.out.println("│ Spring默认     │ 旧版本           │ Spring Boot 2.x+ │");
        System.out.println("└────────────────┴──────────────────┴──────────────────┘");
    }
    
    private static boolean canCastTo(Object obj, Class<?> clazz) {
        try {
            clazz.cast(obj);
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}

/**
 * 有接口的服务类
 */
interface UserService {
    void addUser();
}

class UserServiceImpl implements UserService {
    @Override
    public void addUser() {
        System.out.println("执行业务逻辑: 添加用户");
    }
}

/**
 * 没有接口的服务类（CGLIB 会代理这种类）
 */
class UserManager {
    public void addUser() {
        System.out.println("执行业务逻辑: 添加用户");
    }
}

/**
 * AOP 调用处理器（模拟 Spring AOP 的拦截逻辑）
 */
class AopInvocationHandler implements InvocationHandler {
    private Object target;
    
    public AopInvocationHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置通知（@Before）
        System.out.println("  [AOP-Before] 方法执行前: " + method.getName());
        
        // 执行目标方法
        Object result = method.invoke(target, args);
        
        // 后置通知（@After）
        System.out.println("  [AOP-After] 方法执行后: " + method.getName());
        
        return result;
    }
}
