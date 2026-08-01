package com.designpatterns.decorator.javaio;

import java.io.*;

/**
 * Java IO 类结构演示
 * 展示 FileInputStream、BufferedInputStream、DataInputStream 的定义和关系
 */
public class JavaIOStructureDemo {
    
    public static void main(String[] args) {
        printClassHierarchy();
        System.out.println("\n" + getSeparator() + "\n");
        demonstrateKeyMethods();
    }
    
    /**
     * 展示类继承关系
     */
    private static void printClassHierarchy() {
        System.out.println("【Java IO 类继承关系】\n");
        
        // 1. InputStream - 抽象基类（Component）
        System.out.println("1. InputStream (抽象基类 - Component)");
        System.out.println("   public abstract class InputStream {");
        System.out.println("       public abstract int read() throws IOException;");
        System.out.println("       public int read(byte b[]) throws IOException {...}");
        System.out.println("       public void close() throws IOException {...}");
        System.out.println("   }");
        System.out.println();
        
        // 2. FileInputStream - 具体组件（ConcreteComponent）
        System.out.println("2. FileInputStream (具体组件 - ConcreteComponent)");
        System.out.println("   public class FileInputStream extends InputStream {");
        System.out.println("       private FileDescriptor fd;  // 文件描述符");
        System.out.println("       ");
        System.out.println("       public FileInputStream(String name) {...}");
        System.out.println("       public FileInputStream(File file) {...}");
        System.out.println("       ");
        System.out.println("       @Override");
        System.out.println("       public int read() throws IOException {");
        System.out.println("           // 从文件中读取一个字节");
        System.out.println("           return read0();  // native 方法，直接读取磁盘");
        System.out.println("       }");
        System.out.println("   }");
        System.out.println();
        
        // 3. FilterInputStream - 装饰器基类（Decorator）
        System.out.println("3. FilterInputStream (装饰器基类 - Decorator)");
        System.out.println("   public class FilterInputStream extends InputStream {");
        System.out.println("       protected volatile InputStream in;  // 被装饰的输入流");
        System.out.println("       ");
        System.out.println("       protected FilterInputStream(InputStream in) {");
        System.out.println("           this.in = in;  // 保存被装饰对象的引用");
        System.out.println("       }");
        System.out.println("       ");
        System.out.println("       @Override");
        System.out.println("       public int read() throws IOException {");
        System.out.println("           return in.read();  // 委托给被装饰对象");
        System.out.println("       }");
        System.out.println("   }");
        System.out.println();
        
        // 4. BufferedInputStream - 具体装饰器（ConcreteDecorator）
        System.out.println("4. BufferedInputStream (具体装饰器 - ConcreteDecorator)");
        System.out.println("   public class BufferedInputStream extends FilterInputStream {");
        System.out.println("       private static int DEFAULT_BUFFER_SIZE = 8192;  // 8KB");
        System.out.println("       protected byte buf[];      // 内部缓冲区");
        System.out.println("       protected int count;       // 缓冲区中的字节数");
        System.out.println("       protected int pos;         // 当前读取位置");
        System.out.println("       ");
        System.out.println("       public BufferedInputStream(InputStream in) {");
        System.out.println("           this(in, DEFAULT_BUFFER_SIZE);");
        System.out.println("       }");
        System.out.println("       ");
        System.out.println("       public BufferedInputStream(InputStream in, int size) {");
        System.out.println("           super(in);  // 调用父类，保存被装饰对象");
        System.out.println("           buf = new byte[size];  // 创建缓冲区");
        System.out.println("       }");
        System.out.println("       ");
        System.out.println("       @Override");
        System.out.println("       public synchronized int read() throws IOException {");
        System.out.println("           if (pos >= count) {");
        System.out.println("               fill();  // 缓冲区空了，重新填充");
        System.out.println("           }");
        System.out.println("           return buf[pos++];  // 从缓冲区读取");
        System.out.println("       }");
        System.out.println("       ");
        System.out.println("       private void fill() throws IOException {");
        System.out.println("           // 从被装饰的输入流读取数据到缓冲区");
        System.out.println("           count = in.read(buf, 0, buf.length);");
        System.out.println("       }");
        System.out.println("   }");
        System.out.println();
        
        // 5. DataInputStream - 具体装饰器（ConcreteDecorator）
        System.out.println("5. DataInputStream (具体装饰器 - ConcreteDecorator)");
        System.out.println("   public class DataInputStream extends FilterInputStream");
        System.out.println("                                  implements DataInput {");
        System.out.println("       ");
        System.out.println("       public DataInputStream(InputStream in) {");
        System.out.println("           super(in);  // 调用父类，保存被装饰对象");
        System.out.println("       }");
        System.out.println("       ");
        System.out.println("       // 读取 int 类型（4个字节）");
        System.out.println("       public final int readInt() throws IOException {");
        System.out.println("           int ch1 = in.read();");
        System.out.println("           int ch2 = in.read();");
        System.out.println("           int ch3 = in.read();");
        System.out.println("           int ch4 = in.read();");
        System.out.println("           return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4);");
        System.out.println("       }");
        System.out.println("       ");
        System.out.println("       // 读取 double 类型（8个字节）");
        System.out.println("       public final double readDouble() throws IOException {");
        System.out.println("           return Double.longBitsToDouble(readLong());");
        System.out.println("       }");
        System.out.println("       ");
        System.out.println("       // 读取 UTF-8 字符串");
        System.out.println("       public final String readUTF() throws IOException {");
        System.out.println("           int utflen = readUnsignedShort();");
        System.out.println("           byte[] bytearr = new byte[utflen];");
        System.out.println("           in.read(bytearr, 0, utflen);");
        System.out.println("           return new String(bytearr, \"UTF-8\");");
        System.out.println("       }");
        System.out.println("   }");
    }
    
    /**
     * 演示关键方法的调用流程
     */
    private static void demonstrateKeyMethods() {
        System.out.println("【方法调用流程演示】\n");
        
        System.out.println("场景：DataInputStream + BufferedInputStream + FileInputStream\n");
        System.out.println("代码：");
        System.out.println("DataInputStream dis = new DataInputStream(");
        System.out.println("    new BufferedInputStream(");
        System.out.println("        new FileInputStream(\"data.bin\")");
        System.out.println("    )");
        System.out.println(");\n");
        
        System.out.println("调用 dis.readInt() 时的执行流程：\n");
        System.out.println("1. DataInputStream.readInt()");
        System.out.println("   ├─ 调用 in.read() 读取第1个字节");
        System.out.println("   │  └─ in 是 BufferedInputStream");
        System.out.println("   │");
        System.out.println("   ├─ BufferedInputStream.read()");
        System.out.println("   │  ├─ 检查缓冲区是否为空");
        System.out.println("   │  ├─ 如果空，调用 fill() 填充缓冲区");
        System.out.println("   │  │  └─ 调用 in.read(buf, 0, buf.length)");
        System.out.println("   │  │     └─ in 是 FileInputStream");
        System.out.println("   │  │        └─ FileInputStream 从磁盘读取 8KB 数据");
        System.out.println("   │  └─ 从缓冲区返回一个字节");
        System.out.println("   │");
        System.out.println("   ├─ 重复3次，读取剩余3个字节");
        System.out.println("   │  └─ 由于有缓冲，这3次直接从内存读取，无需访问磁盘");
        System.out.println("   │");
        System.out.println("   └─ 将4个字节组合成 int 返回");
        System.out.println();
        
        System.out.println("关键点：");
        System.out.println("• FileInputStream: 负责实际的磁盘IO");
        System.out.println("• BufferedInputStream: 减少磁盘访问次数（8KB缓冲）");
        System.out.println("• DataInputStream: 提供便捷的类型读取方法");
        System.out.println("• 三者协同工作，各司其职");
    }
    
    private static String getSeparator() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            sb.append("=");
        }
        return sb.toString();
    }
}
