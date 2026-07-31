package com.designpatterns.proxy;

/**
 * 代理类 - 图片代理
 * 延迟加载：只有在真正需要显示时才加载真实图片
 */
public class ImageProxy implements Image {
    private String filename;
    private RealImage realImage;
    
    public ImageProxy(String filename) {
        this.filename = filename;
        System.out.println("创建图片代理: " + filename);
    }
    
    @Override
    public void display() {
        // 延迟加载：只有在第一次调用display时才创建真实对象
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
