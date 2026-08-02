package com.designpatterns.mediator;

/**
 * 标签组件（View）
 * 用于显示状态信息
 */
public class Label extends Component {
    private String text = "";
    
    public Label(DialogMediator mediator) {
        super(mediator);
    }
    
    /**
     * 设置标签文本
     */
    public void setText(String text) {
        this.text = text;
        System.out.println("[状态] " + text);
    }
    
    public String getText() {
        return text;
    }
}
