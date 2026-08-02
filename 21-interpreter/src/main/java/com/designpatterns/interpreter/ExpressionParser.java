package com.designpatterns.interpreter;

import java.util.Stack;

/**
 * 表达式解析器
 * 将字符串表达式解析为表达式对象树
 */
public class ExpressionParser {

    /**
     * 解析表达式字符串
     * 支持格式：数字和运算符用空格分隔，如 "10 + 5 - 3"
     * 
     * @param expressionStr 表达式字符串
     * @return 表达式对象
     */
    public Expression parse(String expressionStr) {
        String[] tokens = expressionStr.split("\\s+");
        Stack<Expression> expressionStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                // 数字直接入栈
                expressionStack.push(new NumberExpression(Integer.parseInt(token)));
            } else if (isOperator(token)) {
                // 处理运算符优先级
                while (!operatorStack.isEmpty() && 
                       getPrecedence(operatorStack.peek()) >= getPrecedence(token)) {
                    buildExpression(expressionStack, operatorStack);
                }
                operatorStack.push(token);
            }
        }

        // 处理剩余的运算符
        while (!operatorStack.isEmpty()) {
            buildExpression(expressionStack, operatorStack);
        }

        return expressionStack.pop();
    }

    /**
     * 构建表达式对象
     */
    private void buildExpression(Stack<Expression> expressionStack, Stack<String> operatorStack) {
        String operator = operatorStack.pop();
        Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();

        Expression expression;
        switch (operator) {
            case "+":
                expression = new AddExpression(left, right);
                break;
            case "-":
                expression = new SubtractExpression(left, right);
                break;
            case "*":
                expression = new MultiplyExpression(left, right);
                break;
            case "/":
                expression = new DivideExpression(left, right);
                break;
            default:
                throw new IllegalArgumentException("不支持的运算符: " + operator);
        }

        expressionStack.push(expression);
    }

    /**
     * 判断是否为数字
     */
    private boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断是否为运算符
     */
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/");
    }

    /**
     * 获取运算符优先级
     */
    private int getPrecedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }
}
