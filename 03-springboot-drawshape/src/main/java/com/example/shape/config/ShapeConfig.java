package com.example.shape.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 图形配置类
 * 从 application.yml 读取配置
 */
@Component
@ConfigurationProperties(prefix = "shape")
public class ShapeConfig {
    private List<ShapeDefinition> shapes = new ArrayList<>();

    public List<ShapeDefinition> getShapes() {
        return shapes;
    }

    public void setShapes(List<ShapeDefinition> shapes) {
        this.shapes = shapes;
    }

    /**
     * 单个图形的定义
     */
    public static class ShapeDefinition {
        private String clazz;
        private Map<String, Object> params = new HashMap<>();  // ★ 改为 Object

        public String getClazz() {
            return clazz;
        }

        public void setClazz(String clazz) {
            this.clazz = clazz;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public String toString() {
            return "ShapeDefinition{clazz='" + clazz + "', params=" + params + "}";
        }
    }
}
