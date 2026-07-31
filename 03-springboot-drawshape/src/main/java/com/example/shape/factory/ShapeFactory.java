package com.example.shape.factory;

import com.example.shape.Shape;
import com.example.shape.config.ShapeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.ConstructorProperties;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * 图形工厂
 * 零 switch、零硬编码、参数顺序无关、支持多种类型
 */
@Component
public class ShapeFactory {

    @Autowired
    private ShapeConfig shapeConfig;

    /**
     * 根据配置创建所有图形
     */
    public List<Shape> createAllShapes() {
        List<Shape> result = new ArrayList<>();
        for (ShapeConfig.ShapeDefinition def : shapeConfig.getShapes()) {
            result.add(createShape(def));
        }
        return result;
    }

    /**
     * ★ 核心方法：根据配置创建单个图形
     * 使用 @ConstructorProperties 注解实现参数名匹配
     * 支持自动类型转换
     */
    private Shape createShape(ShapeConfig.ShapeDefinition def) {
        try {
            // 1. 加载类
            Class<?> clazz = Class.forName(def.getClazz());

            // 2. 检查是否实现了 Shape 接口
            if (!Shape.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Class " + def.getClazz() + " does not implement Shape");
            }

            // 3. 获取所有构造器，找到带 @ConstructorProperties 的
            Map<String, Object> params = def.getParams();  // ★ Map<String, Object>
            for (Constructor<?> constructor : clazz.getConstructors()) {
                ConstructorProperties props = constructor.getAnnotation(ConstructorProperties.class);
                if (props == null) {
                    continue;
                }

                // 4. 获取构造器参数名列表
                String[] paramNames = props.value();

                // 5. 检查 YAML 中是否提供了所有参数
                if (!params.keySet().containsAll(Arrays.asList(paramNames))) {
                    continue;  // 参数名不匹配，尝试下一个构造器
                }

                // 6. ★ 根据构造器参数类型自动转换
                Object[] args = new Object[paramNames.length];
                Class<?>[] paramTypes = constructor.getParameterTypes();
                for (int i = 0; i < paramNames.length; i++) {
                    Object value = params.get(paramNames[i]);
                    args[i] = convertValue(value, paramTypes[i]);
                }

                // 7. 创建实例
                return (Shape) constructor.newInstance(args);
            }

            throw new IllegalArgumentException("No suitable constructor found for: " + def.getClazz());

        } catch (Exception e) {
            throw new RuntimeException("Failed to create shape: " + def.getClazz(), e);
        }
    }

    /**
     * ★ 类型转换工具
     * 支持 Number 类型（int, long, float, double 等）和 String
     */
    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        // 如果已经是目标类型，直接返回
        if (targetType.isInstance(value)) {
            return value;
        }

        // Number 类型转换
        if (value instanceof Number) {
            Number num = (Number) value;
            if (targetType == int.class || targetType == Integer.class) {
                return num.intValue();
            } else if (targetType == double.class || targetType == Double.class) {
                return num.doubleValue();
            } else if (targetType == float.class || targetType == Float.class) {
                return num.floatValue();
            } else if (targetType == long.class || targetType == Long.class) {
                return num.longValue();
            } else if (targetType == short.class || targetType == Short.class) {
                return num.shortValue();
            } else if (targetType == byte.class || targetType == Byte.class) {
                return num.byteValue();
            }
        }

        // String 转换
        if (value instanceof String) {
            String str = (String) value;
            if (targetType == String.class) {
                return str;
            }
            // String 转 Number
            if (targetType == int.class || targetType == Integer.class) {
                return Integer.parseInt(str);
            } else if (targetType == double.class || targetType == Double.class) {
                return Double.parseDouble(str);
            } else if (targetType == float.class || targetType == Float.class) {
                return Float.parseFloat(str);
            } else if (targetType == long.class || targetType == Long.class) {
                return Long.parseLong(str);
            }
        }

        throw new IllegalArgumentException("Cannot convert " + value.getClass() + " to " + targetType);
    }
}
