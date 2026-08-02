package com.designpatterns.interpreter.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL WHERE 条件解释器演示
 * 
 * 展示如何使用解释器模式实现 SQL WHERE 条件过滤
 * 支持 AND、OR、NOT 逻辑运算和比较运算符
 */
public class SqlWhereInterpreterDemo {

    public static void main(String[] args) {
        System.out.println("=== SQL WHERE 条件解释器示例 ===\n");

        // 创建测试数据
        List<User> users = createUsers();
        System.out.println("用户列表：");
        for (User user : users) {
            System.out.println("  " + user);
        }
        System.out.println();

        // 测试各种条件表达式
        testSimpleCondition(users);
        System.out.println("==================================================\n");

        testAndCondition(users);
        System.out.println("==================================================\n");

        testOrCondition(users);
        System.out.println("==================================================\n");

        testComplexCondition(users);
        System.out.println("==================================================\n");

        testNotCondition(users);
    }

    /**
     * 测试简单条件：age > 25
     */
    private static void testSimpleCondition(List<User> users) {
        System.out.println("【测试1】简单条件：age > 25");

        BooleanExpression condition = new CompareExpression("age", ">", 25);
        System.out.println("WHERE " + condition);
        System.out.println("\n匹配的用户：");

        List<User> result = filterUsers(users, condition);
        printUsers(result);
    }

    /**
     * 测试 AND 条件：age > 20 AND status = "active"
     */
    private static void testAndCondition(List<User> users) {
        System.out.println("【测试2】AND 条件：age > 20 AND status = \"active\"");

        BooleanExpression condition = new AndExpression(
            new CompareExpression("age", ">", 20),
            new CompareExpression("status", "=", "active")
        );

        System.out.println("WHERE " + condition);
        System.out.println("\n匹配的用户：");

        List<User> result = filterUsers(users, condition);
        printUsers(result);
    }

    /**
     * 测试 OR 条件：vip = true OR level >= 5
     */
    private static void testOrCondition(List<User> users) {
        System.out.println("【测试3】OR 条件：vip = true OR level >= 5");

        BooleanExpression condition = new OrExpression(
            new CompareExpression("vip", "=", true),
            new CompareExpression("level", ">=", 5)
        );

        System.out.println("WHERE " + condition);
        System.out.println("\n匹配的用户：");

        List<User> result = filterUsers(users, condition);
        printUsers(result);
    }

    /**
     * 测试复杂条件：(age > 25 AND status = "active") OR (vip = true AND level >= 3)
     */
    private static void testComplexCondition(List<User> users) {
        System.out.println("【测试4】复杂条件：(age > 25 AND status = \"active\") OR (vip = true AND level >= 3)");

        BooleanExpression condition = new OrExpression(
            new AndExpression(
                new CompareExpression("age", ">", 25),
                new CompareExpression("status", "=", "active")
            ),
            new AndExpression(
                new CompareExpression("vip", "=", true),
                new CompareExpression("level", ">=", 3)
            )
        );

        System.out.println("WHERE " + condition);
        System.out.println("\n匹配的用户：");

        List<User> result = filterUsers(users, condition);
        printUsers(result);
    }

    /**
     * 测试 NOT 条件：NOT (status = "inactive")
     */
    private static void testNotCondition(List<User> users) {
        System.out.println("【测试5】NOT 条件：NOT (status = \"inactive\")");

        BooleanExpression condition = new NotExpression(
            new CompareExpression("status", "=", "inactive")
        );

        System.out.println("WHERE " + condition);
        System.out.println("\n匹配的用户：");

        List<User> result = filterUsers(users, condition);
        printUsers(result);
    }

    /**
     * 使用条件过滤用户列表
     */
    private static List<User> filterUsers(List<User> users, BooleanExpression condition) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            Context context = user.toContext();
            if (condition.interpret(context)) {
                result.add(user);
            }
        }
        return result;
    }

    /**
     * 打印用户列表
     */
    private static void printUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("  (无匹配结果)");
        } else {
            for (User user : users) {
                System.out.println("  " + user);
            }
        }
    }

    /**
     * 创建测试用户数据
     */
    private static List<User> createUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Alice", 28, "active", 5, true));
        users.add(new User("Bob", 22, "active", 2, false));
        users.add(new User("Charlie", 30, "inactive", 3, false));
        users.add(new User("David", 26, "active", 4, true));
        users.add(new User("Eve", 19, "active", 1, false));
        users.add(new User("Frank", 35, "inactive", 6, true));
        return users;
    }
}
