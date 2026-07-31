package com.designpatterns.proxy;

/**
 * 真实主题 - 真实的图片对象
 * 加载图片需要耗时
 */
public class RealImage implements Image {
    private String filename;
    
    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }
    
    /**
     * 模拟从磁盘加载图片（耗时操作）
     */
    private void loadFromDisk() {
        System.out.println("正在从磁盘加载图片: " + filename + " ...");
        try {
            Thread.sleep(1000); // 模拟耗时操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("图片加载完成: " + filename);
    }
    
    @Override
    public void display() {
        System.out.println("显示图片: " + filename);
    }
}
