package com.designpatterns.proxy;

/**
 * Spring AOP 代理机制说明
 * 
 * Spring AOP 使用两种代理方式，会根据目标对象自动选择：
 * 
 * ┌─────────────────────────────────────────────────────┐
 * │           Spring AOP 代理选择策略                    │
 * ├─────────────────────────────────────────────────────┤
 * │ 1. 目标对象实现了接口 → 默认使用 JDK 动态代理        │
 * │ 2. 目标对象没有实现接口 → 使用 CGLIB 代理           │
 * │ 3. 强制使用 CGLIB → 配置 proxy-target-class=true   │
 * └─────────────────────────────────────────────────────┘
 */
public class SpringAopProxyDemo {
    public static void main(String[] args) {
        System.out.println("=== Spring AOP 代理机制详解 ===\n");
        
        System.out.println("【1】Spring AOP 的两种代理方式\n");
        
        System.out.println("方式一：JDK 动态代理（基于接口）");
        System.out.println("-------------------------------");
        System.out.println("条件：目标类实现了接口");
        System.out.println("示例：");
        System.out.println("  public interface UserService {");
        System.out.println("      void addUser();");
        System.out.println("  }");
        System.out.println("  ");
        System.out.println("  @Service");
        System.out.println("  public class UserServiceImpl implements UserService {");
        System.out.println("      public void addUser() { ... }");
        System.out.println("  }");
        System.out.println("  ");
        System.out.println("结果：Spring 会创建 UserService 接口的代理对象");
        System.out.println("     代理对象类型：$Proxy0（JDK动态代理生成）");
        System.out.println();
        
        System.out.println("方式二：CGLIB 代理（基于继承）");
        System.out.println("-------------------------------");
        System.out.println("条件：目标类没有实现接口");
        System.out.println("示例：");
        System.out.println("  @Service");
        System.out.println("  public class UserService {  // 没有实现接口");
        System.out.println("      public void addUser() { ... }");
        System.out.println("  }");
        System.out.println("  ");
        System.out.println("结果：Spring 会创建 UserService 的子类作为代理");
        System.out.println("     代理对象类型：UserService$$EnhancerBySpringCGLIB$$xxx");
        System.out.println();
        
        System.out.println("\n【2】如何强制使用 CGLIB 代理\n");
        
        System.out.println("Spring Boot 2.x 之前（需要显式配置）：");
        System.out.println("--------------------------------------");
        System.out.println("方式1：在配置类上添加注解");
        System.out.println("  @EnableAspectJAutoProxy(proxyTargetClass = true)");
        System.out.println("  ");
        System.out.println("方式2：在 application.properties 中配置");
        System.out.println("  spring.aop.proxy-target-class=true");
        System.out.println();
        
        System.out.println("Spring Boot 2.x 之后（默认行为）：");
        System.out.println("--------------------------------------");
        System.out.println("✓ 默认就是 proxy-target-class=true");
        System.out.println("✓ 即使实现了接口，也优先使用 CGLIB 代理");
        System.out.println("✓ 原因：CGLIB 代理更灵活，避免接口转换问题");
        System.out.println();
        
        System.out.println("\n【3】两种代理方式的对比\n");
        System.out.println("┌──────────────┬────────────────────┬────────────────────┐");
        System.out.println("│   特性       │   JDK动态代理      │   CGLIB代理        │");
        System.out.println("├──────────────┼────────────────────┼────────────────────┤");
        System.out.println("│ 实现方式     │ 实现接口           │ 继承目标类         │");
        System.out.println("│ 要求         │ 必须有接口         │ 不能是final类      │");
        System.out.println("│ 代理对象类型 │ 接口类型           │ 目标类的子类       │");
        System.out.println("│ 方法调用     │ 反射调用           │ FastClass机制      │");
        System.out.println("│ 性能         │ 略慢               │ 略快               │");
        System.out.println("│ 依赖         │ JDK内置            │ 需要cglib库        │");
        System.out.println("│ Spring默认   │ 实现了接口时使用   │ 没有接口时使用     │");
        System.out.println("└──────────────┴────────────────────┴────────────────────┘");
        
        System.out.println("\n【4】实际代码示例\n");
        
        System.out.println("场景1：有接口（JDK动态代理）");
        System.out.println("```java");
        System.out.println("@Service");
        System.out.println("public class UserServiceImpl implements UserService {");
        System.out.println("    @Override");
        System.out.println("    public void addUser() {");
        System.out.println("        System.out.println(\"添加用户\");");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("");
        System.out.println("@Aspect");
        System.out.println("@Component");
        System.out.println("public class LogAspect {");
        System.out.println("    @Before(\"execution(* com.example.service.*.*(..))\")");
        System.out.println("    public void before() {");
        System.out.println("        System.out.println(\"[AOP] 方法执行前\");");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("");
        System.out.println("// 注入时使用接口类型");
        System.out.println("@Autowired");
        System.out.println("private UserService userService;  // ✅ 正确");
        System.out.println("");
        System.out.println("// 代理对象类型：com.sun.proxy.$Proxy123 (JDK代理)");
        System.out.println("```");
        System.out.println();
        
        System.out.println("场景2：无接口（CGLIB代理）");
        System.out.println("```java");
        System.out.println("@Service");
        System.out.println("public class UserService {  // 没有实现接口");
        System.out.println("    public void addUser() {");
        System.out.println("        System.out.println(\"添加用户\");");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("");
        System.out.println("// 注入时使用具体类类型");
        System.out.println("@Autowired");
        System.out.println("private UserService userService;  // ✅ 正确");
        System.out.println("");
        System.out.println("// 代理对象类型：UserService$$EnhancerBySpringCGLIB$$xxx");
        System.out.println("```");
        
        System.out.println("\n【5】如何查看实际使用的代理类型\n");
        System.out.println("在 Spring 应用中打印代理对象的类名：");
        System.out.println("```java");
        System.out.println("System.out.println(userService.getClass().getName());");
        System.out.println("```");
        System.out.println("");
        System.out.println("输出结果：");
        System.out.println("- JDK代理：com.sun.proxy.$Proxy123");
        System.out.println("- CGLIB代理：com.example.UserService$$EnhancerBySpringCGLIB$$12345678");
        
        System.out.println("\n【6】总结\n");
        System.out.println("✓ Spring AOP 同时支持 JDK 动态代理和 CGLIB 代理");
        System.out.println("✓ Spring Boot 2.x+ 默认使用 CGLIB 代理");
        System.out.println("✓ 有接口但想用具体类注入 → 必须用 CGLIB 代理");
        System.out.println("✓ final 类和 final 方法无法被 CGLIB 代理");
        System.out.println("✓ 性能差异很小，一般不需要关心");
    }
}
