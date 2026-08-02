package com.designpatterns.interpreter.sql;

/**
 * 比较表达式（终结符表达式）
 * 用于比较变量和常量，如：age > 18, status = "active"
 */
public class CompareExpression implements BooleanExpression {
    private String variable;    // 变量名
    private String operator;    // 运算符: >, <, >=, <=, =, !=
    private Object value;       // 比较的值

    public CompareExpression(String variable, String operator, Object value) {
        this.variable = variable;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public boolean interpret(Context context) {
        if (!context.contains(variable)) {
            throw new IllegalArgumentException("变量不存在: " + variable);
        }

        Object varValue = context.get(variable);

        // 处理 null 值
        if (varValue == null || value == null) {
            if ("=".equals(operator)) {
                return varValue == value;
            } else if ("!=".equals(operator)) {
                return varValue != value;
            }
            return false;
        }

        // 数字比较
        if (varValue instanceof Number && value instanceof Number) {
            return compareNumbers((Number) varValue, (Number) value);
        }

        // 字符串比较
        if (varValue instanceof String && value instanceof String) {
            return compareStrings((String) varValue, (String) value);
        }

        // 布尔值比较
        if (varValue instanceof Boolean && value instanceof Boolean) {
            return compareBooleans((Boolean) varValue, (Boolean) value);
        }

        throw new IllegalArgumentException("不支持的类型比较: " + varValue.getClass() + " 和 " + value.getClass());
    }

    /**
     * 数字比较
     */
    private boolean compareNumbers(Number v1, Number v2) {
        double d1 = v1.doubleValue();
        double d2 = v2.doubleValue();

        switch (operator) {
            case ">":
                return d1 > d2;
            case "<":
                return d1 < d2;
            case ">=":
                return d1 >= d2;
            case "<=":
                return d1 <= d2;
            case "=":
                return d1 == d2;
            case "!=":
                return d1 != d2;
            default:
                throw new IllegalArgumentException("不支持的运算符: " + operator);
        }
    }

    /**
     * 字符串比较
     */
    private boolean compareStrings(String s1, String s2) {
        switch (operator) {
            case "=":
                return s1.equals(s2);
            case "!=":
                return !s1.equals(s2);
            case ">":
                return s1.compareTo(s2) > 0;
            case "<":
                return s1.compareTo(s2) < 0;
            case ">=":
                return s1.compareTo(s2) >= 0;
            case "<=":
                return s1.compareTo(s2) <= 0;
            default:
                throw new IllegalArgumentException("不支持的运算符: " + operator);
        }
    }

    /**
     * 布尔值比较
     */
    private boolean compareBooleans(Boolean b1, Boolean b2) {
        switch (operator) {
            case "=":
                return b1.equals(b2);
            case "!=":
                return !b1.equals(b2);
            default:
                throw new IllegalArgumentException("布尔值只支持 = 和 != 运算符");
        }
    }

    @Override
    public String toString() {
        String valueStr = value instanceof String ? "\"" + value + "\"" : String.valueOf(value);
        return variable + " " + operator + " " + valueStr;
    }
}
