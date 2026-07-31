package com.example.shape.client;

import com.example.shape.Shape;
import com.example.shape.factory.ShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

/**
 * Spring Boot 启动类
 */
@SpringBootApplication(scanBasePackages = "com.example.shape")
@EnableConfigurationProperties
public class Application implements CommandLineRunner {

    @Autowired
    private ShapeFactory shapeFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("========== 开始绘制所有图形 ==========");
        List<Shape> shapes = shapeFactory.createAllShapes();
        for (Shape shape : shapes) {
            shape.draw();
        }
        System.out.println("========== 绘制完成 ==========");
    }
}
