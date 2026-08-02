package com.designpatterns.composite;

/**
 * 组织架构组件抽象类
 * 定义了叶子节点和组合节点的统一接口
 */
public abstract class OrganizationComponent {
    protected String name;

    public OrganizationComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 显示组织结构
     * @param indent 缩进层级
     */
    public abstract void display(int indent);

    /**
     * 计算总人数
     */
    public abstract int getEmployeeCount();

    /**
     * 计算总薪资
     */
    public abstract double getTotalSalary();

    /**
     * 添加子节点（默认不支持，由组合节点覆盖）
     */
    public void add(OrganizationComponent component) {
        throw new UnsupportedOperationException("该节点不支持添加子节点");
    }

    /**
     * 移除子节点（默认不支持，由组合节点覆盖）
     */
    public void remove(OrganizationComponent component) {
        throw new UnsupportedOperationException("该节点不支持移除子节点");
    }

    /**
     * 生成缩进字符串
     */
    protected String getIndent(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
}
