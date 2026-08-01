package com.designpatterns.decorator.javaio;

import java.io.*;

/**
 * Java IO 装饰器模式演示
 * Java的IO流是装饰器模式的经典应用
 */
public class JavaIODecoratorDemo {
    
    public static void main(String[] args) {
        demonstrateInputStreamDecorators();
        printSeparator();
        demonstrateOutputStreamDecorators();
        printSeparator();
        demonstrateCustomDecorator();
    }
    
    private static void printSeparator() {
        System.out.println();
        for (int i = 0; i < 50; i++) {
            System.out.print("=");
        }
        System.out.println("\n");
    }
    
    /**
     * 演示InputStream装饰器
     */
    private static void demonstrateInputStreamDecorators() {
        System.out.println("【InputStream 装饰器演示】");
        
        String testFile = "test-input.txt";
        
        try {
            // 先写入测试数据
            FileOutputStream fos = new FileOutputStream(testFile);
            fos.write("Hello Java IO Decorator Pattern!\n这是装饰器模式的示例。".getBytes("UTF-8"));
            fos.close();
            
            // 1. 基础组件：FileInputStream
            System.out.println("\n1. 使用 FileInputStream (基础组件):");
            FileInputStream fis = new FileInputStream(testFile);
            int data;
            int count = 0;
            while ((data = fis.read()) != -1 && count++ < 20) {
                System.out.print((char) data);
            }
            fis.close();
            System.out.println("...");
            
            // 2. 装饰器：BufferedInputStream（添加缓冲功能）
            System.out.println("\n2. 使用 BufferedInputStream (装饰器 - 添加缓冲):");
            BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(testFile)
            );
            System.out.println("   - 提供了缓冲功能，提高读取效率");
            System.out.println("   - 可以使用 mark() 和 reset() 方法");
            bis.close();
            
            // 3. 装饰器：DataInputStream（添加读取基本类型功能）
            System.out.println("\n3. 使用 DataInputStream (装饰器 - 读取基本类型):");
            
            // 先写入一些基本类型数据
            DataOutputStream dos = new DataOutputStream(
                new FileOutputStream("test-data.bin")
            );
            dos.writeInt(42);
            dos.writeDouble(3.14159);
            dos.writeUTF("装饰器模式");
            dos.close();
            
            // 读取基本类型数据
            DataInputStream dis = new DataInputStream(
                new FileInputStream("test-data.bin")
            );
            System.out.println("   - 读取 int: " + dis.readInt());
            System.out.println("   - 读取 double: " + dis.readDouble());
            System.out.println("   - 读取 UTF: " + dis.readUTF());
            dis.close();
            
            // 4. 多重装饰：BufferedInputStream + DataInputStream
            System.out.println("\n4. 多重装饰 BufferedInputStream + DataInputStream:");
            DataInputStream multiDis = new DataInputStream(
                new BufferedInputStream(
                    new FileInputStream("test-data.bin")
                )
            );
            System.out.println("   - 同时具有缓冲功能和读取基本类型功能");
            multiDis.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 清理测试文件
            new File(testFile).delete();
            new File("test-data.bin").delete();
        }
    }
    
    /**
     * 演示OutputStream装饰器
     */
    private static void demonstrateOutputStreamDecorators() {
        System.out.println("【OutputStream 装饰器演示】");
        
        String testFile = "test-output.txt";
        
        try {
            // 1. 基础组件：FileOutputStream
            System.out.println("\n1. FileOutputStream (基础组件):");
            System.out.println("   - 提供基本的文件写入功能");
            
            // 2. 装饰器：BufferedOutputStream
            System.out.println("\n2. BufferedOutputStream (装饰器 - 添加缓冲):");
            BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(testFile)
            );
            bos.write("带缓冲的输出流\n".getBytes("UTF-8"));
            System.out.println("   - 提供缓冲功能，减少实际写入次数");
            bos.close();
            
            // 3. 装饰器：PrintStream
            System.out.println("\n3. PrintStream (装饰器 - 添加格式化输出):");
            PrintStream ps = new PrintStream(
                new FileOutputStream(testFile, true)
            );
            ps.println("PrintStream 提供了便捷的输出方法");
            ps.printf("格式化输出: %d, %.2f\n", 100, 3.14);
            System.out.println("   - 提供 println(), printf() 等便捷方法");
            ps.close();
            
            // 4. 多重装饰
            System.out.println("\n4. 多重装饰 BufferedOutputStream + PrintStream:");
            PrintStream multiPs = new PrintStream(
                new BufferedOutputStream(
                    new FileOutputStream(testFile, true)
                )
            );
            multiPs.println("同时具有缓冲和格式化功能");
            System.out.println("   - 既有缓冲提高效率，又有格式化便捷性");
            multiPs.close();
            
            // 读取并显示文件内容
            System.out.println("\n文件内容:");
            BufferedReader br = new BufferedReader(new FileReader(testFile));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("   " + line);
            }
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            new File(testFile).delete();
        }
    }
    
    /**
     * 演示自定义装饰器
     */
    private static void demonstrateCustomDecorator() {
        System.out.println("【自定义装饰器演示】");
        
        String testFile = "test-custom.txt";
        
        try {
            // 写入测试数据
            FileOutputStream fos = new FileOutputStream(testFile);
            fos.write("Hello World".getBytes());
            fos.close();
            
            // 使用自定义的大写转换装饰器
            System.out.println("\n使用自定义 UpperCaseInputStream:");
            InputStream in = new UpperCaseInputStream(
                new BufferedInputStream(
                    new FileInputStream(testFile)
                )
            );
            
            int c;
            System.out.print("   原始: Hello World -> 转换后: ");
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();
            in.close();
            
            System.out.println("   - 自定义装饰器将所有字符转换为大写");
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            new File(testFile).delete();
        }
    }
}
