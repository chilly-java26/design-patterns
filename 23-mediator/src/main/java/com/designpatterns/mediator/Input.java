package com.designpatterns.mediator;

/**
 * 输入框组件（View）
 * 只负责接收用户输入和显示，不关心其他组件
 */
public class Input extends Component {
    private String name;
    private String value = "";
    private boolean enabled = true;
    
    public Input(DialogMediator mediator, String name) {
        super(mediator);
        this.name = name;
    }
    
    /**
     * 设置输入值，通知中介者数据变化
     */
    public void setValue(String value) {
        if (!enabled) {
            System.out.println("[" + name + "] 输入框已禁用，无法输入");
            return;
        }
        this.value = value;
        System.out.println("[" + name + "] 输入: " + value);
        // 通知中介者输入发生变化
        mediator.notify(this, "input_changed");
    }
    
    public String getValue() {
        return value;
    }
    
    public void clear() {
        value = "";
        System.out.println("[" + name + "] 已清空");
    }
    
    public void disable() {
        enabled = false;
        System.out.println("[" + name + "] 输入框已禁用");
    }
    
    public void enable() {
        enabled = true;
        System.out.println("[" + name + "] 输入框已启用");
    }
    
    public String getName() {
        return name;
    }
}
