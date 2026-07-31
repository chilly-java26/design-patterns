package com.designpatterns.proxy;

/**
 * CGLIB 动态代理演示（概念说明）
 * 
 * CGLIB 是另一种动态代理方式，通过继承实现代理
 * 与 JDK 动态代理的区别：
 * 
 * 1. JDK 动态代理：
 *    - 基于接口
 *    - 目标对象必须实现接口
 *    - 生成接口的代理实现类
 *    - JDK 自带，无需额外依赖
 * 
 * 2. CGLIB 动态代理：
 *    - 基于继承
 *    - 目标对象可以没有接口
 *    - 生成目标类的子类
 *    - 需要引入 CGLIB 依赖
 * 
 * 使用示例（需要添加 CGLIB 依赖）：
 * 
 * <dependency>
 *     <groupId>cglib</groupId>
 *     <artifactId>cglib</artifactId>
 *     <version>3.3.0</version>
 * </dependency>
 * 
 * Enhancer enhancer = new Enhancer();
 * enhancer.setSuperclass(RealImage.class);
 * enhancer.setCallback(new MethodInterceptor() {
 *     public Object intercept(Object obj, Method method, Object[] args, 
 *                            MethodProxy proxy) throws Throwable {
 *         System.out.println("前置增强");
 *         Object result = proxy.invokeSuper(obj, args);
 *         System.out.println("后置增强");
 *         return result;
 *     }
 * });
 * RealImage proxy = (RealImage) enhancer.create();
 * 
 * Spring AOP 中：
 * - 如果目标对象实现了接口 → 使用 JDK 动态代理
 * - 如果目标对象没有接口 → 使用 CGLIB 代理
 */
public class CglibProxyDemo {
    public static void main(String[] args) {
        System.out.println("=== CGLIB 动态代理说明 ===\n");
        
        System.out.println("CGLIB（Code Generation Library）动态代理：");
        System.out.println("1. 通过继承实现代理（而不是接口）");
        System.out.println("2. 可以代理没有接口的类");
        System.out.println("3. 不能代理 final 类和 final 方法");
        System.out.println("4. Spring AOP 底层使用");
        System.out.println();
        
        System.out.println("JDK 动态代理 vs CGLIB 动态代理：");
        System.out.println("┌──────────────┬──────────────┬──────────────┐");
        System.out.println("│    特性      │ JDK动态代理  │ CGLIB代理    │");
        System.out.println("├──────────────┼──────────────┼──────────────┤");
        System.out.println("│ 实现方式     │ 基于接口     │ 基于继承     │");
        System.out.println("│ 是否需要接口 │ 必须有接口   │ 不需要接口   │");
        System.out.println("│ final类/方法 │ 不受限制     │ 不能代理     │");
        System.out.println("│ 性能         │ 略快         │ 略慢         │");
        System.out.println("│ JDK依赖      │ JDK自带      │ 需要第三方库 │");
        System.out.println("└──────────────┴──────────────┴──────────────┘");
        System.out.println();
        
        System.out.println("本项目使用的是 JDK 动态代理，因为我们有 Image 接口");
        System.out.println("如果需要代理一个没有接口的类，就需要使用 CGLIB");
    }
}
