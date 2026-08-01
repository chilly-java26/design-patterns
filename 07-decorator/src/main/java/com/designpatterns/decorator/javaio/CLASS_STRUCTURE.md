# Java IO 类结构详解

## 一、类继承关系图

```
                    InputStream (抽象基类)
                         |
        +----------------+----------------+
        |                                 |
  FileInputStream                 FilterInputStream
  (具体组件)                       (装饰器基类)
                                          |
                        +-----------------+-----------------+
                        |                                   |
              BufferedInputStream                   DataInputStream
              (具体装饰器)                           (具体装饰器)
```

## 二、各类详细定义

### 1. InputStream (Component - 组件接口)

```java
public abstract class InputStream implements Closeable {
    // 核心抽象方法：读取一个字节
    public abstract int read() throws IOException;
    
    // 读取多个字节到数组
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }
    
    // 读取指定数量的字节
    public int read(byte b[], int off, int len) throws IOException;
    
    // 跳过指定数量的字节
    public long skip(long n) throws IOException;
    
    // 关闭流
    public void close() throws IOException {}
}
```

**角色**：定义所有输入流的基本接口  
**职责**：规定所有输入流必须实现的方法

---

### 2. FileInputStream (ConcreteComponent - 具体组件)

```java
public class FileInputStream extends InputStream {
    // 文件描述符
    private final FileDescriptor fd;
    
    // 文件路径
    private final String path;
    
    // 构造函数：通过文件名创建
    public FileInputStream(String name) throws FileNotFoundException {
        this(name != null ? new File(name) : null);
    }
    
    // 构造函数：通过File对象创建
    public FileInputStream(File file) throws FileNotFoundException {
        // 打开文件，获取文件描述符
        fd = new FileDescriptor();
        path = file.getPath();
        open(path);
    }
    
    // 打开文件（native方法）
    private native void open(String name) throws FileNotFoundException;
    
    // 读取一个字节（native方法）
    @Override
    public int read() throws IOException {
        return read0();
    }
    
    private native int read0() throws IOException;
    
    // 读取多个字节到数组（native方法）
    @Override
    public int read(byte b[]) throws IOException {
        return readBytes(b, 0, b.length);
    }
    
    private native int readBytes(byte b[], int off, int len) throws IOException;
}
```

**角色**：具体组件，实现基本功能  
**职责**：从文件中读取字节数据  
**特点**：
- 直接与操作系统交互（native方法）
- 每次读取都会进行系统调用
- 没有缓冲，效率较低

---

### 3. FilterInputStream (Decorator - 装饰器基类)

```java
public class FilterInputStream extends InputStream {
    // 【核心】被装饰的输入流
    protected volatile InputStream in;
    
    // 【核心】构造函数：接收被装饰对象
    protected FilterInputStream(InputStream in) {
        this.in = in;
    }
    
    // 委托给被装饰对象
    @Override
    public int read() throws IOException {
        return in.read();
    }
    
    @Override
    public int read(byte b[]) throws IOException {
        return in.read(b, 0, b.length);
    }
    
    @Override
    public long skip(long n) throws IOException {
        return in.skip(n);
    }
    
    @Override
    public void close() throws IOException {
        in.close();
    }
}
```

**角色**：装饰器抽象类  
**职责**：
1. 持有一个 InputStream 的引用
2. 将所有方法委托给被装饰对象
3. 为具体装饰器提供基础框架

**关键点**：
- `protected InputStream in` - 这是装饰器模式的核心
- 所有方法都简单委托，不添加额外功能
- 子类可以选择性地重写方法来增强功能

---

### 4. BufferedInputStream (ConcreteDecorator - 具体装饰器)

```java
public class BufferedInputStream extends FilterInputStream {
    // 默认缓冲区大小：8KB
    private static int DEFAULT_BUFFER_SIZE = 8192;
    
    // 内部缓冲区
    protected volatile byte buf[];
    
    // 缓冲区中有效字节数
    protected int count;
    
    // 当前读取位置
    protected int pos;
    
    // 标记位置（用于reset）
    protected int markpos = -1;
    
    // 标记限制
    protected int marklimit;
    
    // 构造函数1：使用默认缓冲区大小
    public BufferedInputStream(InputStream in) {
        this(in, DEFAULT_BUFFER_SIZE);
    }
    
    // 构造函数2：指定缓冲区大小
    public BufferedInputStream(InputStream in, int size) {
        super(in);  // 调用父类，保存被装饰对象
        if (size <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        buf = new byte[size];  // 创建缓冲区
    }
    
    // 【核心】填充缓冲区
    private void fill() throws IOException {
        // 处理标记
        if (markpos < 0) {
            pos = 0;  // 没有标记，从头开始
        } else if (pos >= buf.length) {
            // 缓冲区满了的处理逻辑...
        }
        
        // 从被装饰的输入流读取数据到缓冲区
        count = pos;
        int n = getInIfOpen().read(buf, pos, buf.length - pos);
        if (n > 0) {
            count = n + pos;
        }
    }
    
    // 【核心】重写read方法，添加缓冲功能
    @Override
    public synchronized int read() throws IOException {
        if (pos >= count) {
            fill();  // 缓冲区空了，重新填充
            if (pos >= count) {
                return -1;  // 没有更多数据
            }
        }
        return getBufIfOpen()[pos++] & 0xff;  // 从缓冲区读取
    }
    
    // 批量读取
    @Override
    public synchronized int read(byte b[], int off, int len) throws IOException {
        // 先尝试从缓冲区读取
        int avail = count - pos;
        if (avail <= 0) {
            // 缓冲区空了
            if (len >= buf.length && markpos < 0) {
                // 如果要读的数据比缓冲区还大，直接从底层流读取
                return getInIfOpen().read(b, off, len);
            }
            fill();  // 填充缓冲区
            avail = count - pos;
            if (avail <= 0) return -1;
        }
        int cnt = (avail < len) ? avail : len;
        System.arraycopy(buf, pos, b, off, cnt);
        pos += cnt;
        return cnt;
    }
    
    // 标记当前位置
    @Override
    public synchronized void mark(int readlimit) {
        marklimit = readlimit;
        markpos = pos;
    }
    
    // 重置到标记位置
    @Override
    public synchronized void reset() throws IOException {
        if (markpos < 0) {
            throw new IOException("Resetting to invalid mark");
        }
        pos = markpos;
    }
}
```

**角色**：具体装饰器  
**新增功能**：
1. **缓冲机制**：减少实际IO次数
2. **mark/reset**：可以回退到之前的位置

**工作原理**：
1. 维护一个8KB的内部字节数组
2. 第一次读取时，一次性从底层流读取8KB到缓冲区
3. 后续读取直接从缓冲区返回，直到缓冲区空了
4. 大大减少了系统调用次数

---

### 5. DataInputStream (ConcreteDecorator - 具体装饰器)

```java
public class DataInputStream extends FilterInputStream implements DataInput {
    
    // 构造函数
    public DataInputStream(InputStream in) {
        super(in);  // 调用父类，保存被装饰对象
    }
    
    // 【新增功能】读取 boolean
    public final boolean readBoolean() throws IOException {
        int ch = in.read();
        if (ch < 0) throw new EOFException();
        return (ch != 0);
    }
    
    // 【新增功能】读取 byte
    public final byte readByte() throws IOException {
        int ch = in.read();
        if (ch < 0) throw new EOFException();
        return (byte)(ch);
    }
    
    // 【新增功能】读取 short (2字节)
    public final short readShort() throws IOException {
        int ch1 = in.read();
        int ch2 = in.read();
        if ((ch1 | ch2) < 0) throw new EOFException();
        return (short)((ch1 << 8) + (ch2 << 0));
    }
    
    // 【新增功能】读取 int (4字节)
    public final int readInt() throws IOException {
        int ch1 = in.read();
        int ch2 = in.read();
        int ch3 = in.read();
        int ch4 = in.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0) throw new EOFException();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }
    
    // 【新增功能】读取 long (8字节)
    public final long readLong() throws IOException {
        // 读取8个字节并组合
        return ((long)read() << 56) +
               ((long)(read() & 255) << 48) +
               ((long)(read() & 255) << 40) +
               ((long)(read() & 255) << 32) +
               ((long)(read() & 255) << 24) +
               ((read() & 255) << 16) +
               ((read() & 255) << 8) +
               ((read() & 255) << 0);
    }
    
    // 【新增功能】读取 float (4字节)
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }
    
    // 【新增功能】读取 double (8字节)
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }
    
    // 【新增功能】读取 UTF-8 字符串
    public final String readUTF() throws IOException {
        return readUTF(this);
    }
    
    public static final String readUTF(DataInput in) throws IOException {
        // 先读取字符串长度（2字节）
        int utflen = in.readUnsignedShort();
        
        byte[] bytearr = new byte[utflen];
        char[] chararr = new char[utflen];
        
        int c, char2, char3;
        int count = 0;
        int chararr_count = 0;
        
        // 读取字节数据
        in.readFully(bytearr, 0, utflen);
        
        // 解码UTF-8
        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            if (c > 127) break;
            count++;
            chararr[chararr_count++] = (char)c;
        }
        
        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            switch (c >> 4) {
                case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
                    count++;
                    chararr[chararr_count++] = (char)c;
                    break;
                case 12: case 13:
                    count += 2;
                    char2 = (int) bytearr[count-1];
                    chararr[chararr_count++] = (char)(((c & 0x1F) << 6) | (char2 & 0x3F));
                    break;
                case 14:
                    count += 3;
                    char2 = (int) bytearr[count-2];
                    char3 = (int) bytearr[count-1];
                    chararr[chararr_count++] = (char)(((c & 0x0F) << 12) |
                                                      ((char2 & 0x3F) << 6) |
                                                      ((char3 & 0x3F) << 0));
                    break;
            }
        }
        
        return new String(chararr, 0, chararr_count);
    }
}
```

**角色**：具体装饰器  
**新增功能**：读取Java基本数据类型
- `readBoolean()` - 读取布尔值
- `readByte()` - 读取字节
- `readShort()` - 读取短整型
- `readInt()` - 读取整型
- `readLong()` - 读取长整型
- `readFloat()` - 读取浮点数
- `readDouble()` - 读取双精度浮点数
- `readUTF()` - 读取UTF-8字符串

**工作原理**：
- 内部调用多次 `in.read()` 读取多个字节
- 按照Java的字节序（大端序）组合成相应类型
- 不维护额外状态，纯粹的类型转换装饰

---

## 三、三者对比总结

| 类名 | 角色 | 主要职责 | 是否持有其他流 | 新增功能 |
|-----|------|---------|--------------|---------|
| **FileInputStream** | 具体组件 | 从文件读取字节 | 否 | - |
| **BufferedInputStream** | 具体装饰器 | 添加缓冲功能 | 是 (持有InputStream) | 8KB缓冲区、mark/reset |
| **DataInputStream** | 具体装饰器 | 读取基本类型 | 是 (持有InputStream) | readInt/readDouble/readUTF等 |

## 四、装饰器模式的关键要素

### 1. 组件接口 (Component)
```java
InputStream  // 定义统一接口
```

### 2. 具体组件 (ConcreteComponent)
```java
FileInputStream  // 实现基本功能
```

### 3. 装饰器基类 (Decorator)
```java
FilterInputStream {
    protected InputStream in;  // 【关键】持有组件引用
}
```

### 4. 具体装饰器 (ConcreteDecorator)
```java
BufferedInputStream  // 在基础上添加缓冲
DataInputStream      // 在基础上添加类型读取
```

## 五、使用示例

```java
// 1. 只用基础组件
InputStream in1 = new FileInputStream("file.txt");

// 2. 基础组件 + 缓冲装饰
InputStream in2 = new BufferedInputStream(
    new FileInputStream("file.txt")
);

// 3. 基础组件 + 类型装饰
DataInputStream in3 = new DataInputStream(
    new FileInputStream("data.bin")
);

// 4. 基础组件 + 缓冲装饰 + 类型装饰（推荐）
DataInputStream in4 = new DataInputStream(
    new BufferedInputStream(
        new FileInputStream("data.bin")
    )
);
```

**最佳实践**：同时使用 BufferedInputStream 和 DataInputStream，既快又方便！
