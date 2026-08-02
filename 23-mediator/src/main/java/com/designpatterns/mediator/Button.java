package com.designpatterns.mediator;

/**
 * 按钮组件（View）
 * 只负责自己的显示和点击行为，不关心其他组件
 */
public class Button extends Component {
    private String label;
    private boolean enabled = true;
    
    public Button(DialogMediator mediator, String label) {
        super(mediator);
        this.label = label;
    }
    
    /**
     * 点击按钮，通知中介者
     */
    public void click() {
        if (!enabled) {
            System.out.println("[" + label + "] 按钮已禁用，无法点击");
            return;
        }
        System.out.println("[" + label + "] 按钮被点击");
        // 将事件传递给中介者，让中介者决定如何处理
        mediator.notify(this, label.toLowerCase() + "_clicked");
    }
    
    public void enable() {
        enabled = true;
        System.out.println("[" + label + "] 按钮启用");
    }
    
    public void disable() {
        enabled = false;
        System.out.println("[" + label + "] 按钮禁用");
    }
    
    public String getLabel() {
        return label;
    }
}
