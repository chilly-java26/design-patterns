package com.designpatterns.proxy;

/**
 * 代理模式演示
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== 代理模式演示 ===\n");
        
        System.out.println("场景：图片浏览器，需要加载3张图片\n");
        
        // 1. 创建代理对象（很快，不加载真实图片）
        System.out.println("1. 创建图片代理对象:");
        Image image1 = new ImageProxy("photo1.jpg");
        Image image2 = new ImageProxy("photo2.jpg");
        Image image3 = new ImageProxy("photo3.jpg");
        System.out.println("✓ 代理对象创建完成，速度很快！\n");
        
        // 2. 显示第一张图片（触发真实加载）
        System.out.println("2. 用户打开第一张图片:");
        image1.display();
        System.out.println();
        
        // 3. 再次显示第一张图片（不需要重新加载）
        System.out.println("3. 用户再次查看第一张图片:");
        image1.display();
        System.out.println("✓ 已经加载过了，直接显示，无需重新加载！\n");
        
        // 4. 显示第二张图片
        System.out.println("4. 用户打开第二张图片:");
        image2.display();
        System.out.println();
        
        // 5. 第三张图片从未被显示，所以不会加载
        System.out.println("5. 第三张图片:");
        System.out.println("用户没有打开第三张图片，所以它从未被加载");
        System.out.println("✓ 节省了加载时间和内存！");
    }
}
