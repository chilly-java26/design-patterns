package com.designpatterns.interpreter;

/**
 * 解释器模式演示
 * 
 * 解释器模式核心思想：
 * 将语法规则（加减乘除运算）转化为对象，通过对象组合构建表达式树，
 * 递归调用 interpret() 方法完成计算。
 * 
 * 优点：
 * 1. 易于扩展新的语法规则（添加新运算符只需新增类）
 * 2. 表达式可以被序列化、持久化、传递
 * 3. 符合开闭原则
 * 
 * 缺点：
 * 1. 复杂文法难以维护（类的数量会增加）
 * 2. 执行效率较低（递归调用）
 */
public class InterpreterDemo {

    public static void main(String[] args) {
        System.out.println("=== 解释器模式示例 ===\n");

        // 方式1：手动构建表达式树
        System.out.println("方式1：手动构建表达式树");
        manualBuild();

        System.out.println("\n==================================================\n");

        // 方式2：使用解析器解析字符串表达式
        System.out.println("方式2：使用解析器解析字符串表达式");
        parseExpression();
    }

    /**
     * 手动构建表达式树
     */
    private static void manualBuild() {
        // 构建表达式：(10 + 5) - 3
        Expression expr1 = new SubtractExpression(
            new AddExpression(
                new NumberExpression(10),
                new NumberExpression(5)
            ),
            new NumberExpression(3)
        );

        System.out.println("表达式: " + expr1);
        System.out.println("计算结果: " + expr1.interpret());
        System.out.println();

        // 构建表达式：(20 * 3) + (8 / 2)
        Expression expr2 = new AddExpression(
            new MultiplyExpression(
                new NumberExpression(20),
                new NumberExpression(3)
            ),
            new DivideExpression(
                new NumberExpression(8),
                new NumberExpression(2)
            )
        );

        System.out.println("表达式: " + expr2);
        System.out.println("计算结果: " + expr2.interpret());
    }

    /**
     * 使用解析器解析字符串表达式
     */
    private static void parseExpression() {
        ExpressionParser parser = new ExpressionParser();

        // 测试用例
        String[] expressions = {
            "10 + 5 - 3",
            "20 * 3 + 8 / 2",
            "100 - 50 + 20",
            "6 * 7 + 8",
            "100 / 5 - 10"
        };

        for (String exprStr : expressions) {
            Expression expr = parser.parse(exprStr);
            System.out.println("表达式: " + exprStr);
            System.out.println("解析树: " + expr);
            System.out.println("计算结果: " + expr.interpret());
            System.out.println();
        }

        // 测试除零异常
        System.out.println("测试异常情况：");
        try {
            Expression errorExpr = parser.parse("10 / 0");
            System.out.println("表达式: 10 / 0");
            System.out.println("计算结果: " + errorExpr.interpret());
        } catch (ArithmeticException e) {
            System.out.println("捕获异常: " + e.getMessage());
        }
    }
}
