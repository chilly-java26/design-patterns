package com.designpatterns.bridge;

import com.designpatterns.bridge.abstraction.*;
import com.designpatterns.bridge.implementation.*;

/**
 * 桥接模式演示
 * 演示格式化和存储两个维度的独立变化
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== 桥接模式演示：日志收集系统 ===\n");
        
        // 测试所有20种组合（4种格式 × 5种存储）
        testAllCombinations();
        
        System.out.println();
        printSeparator();
        
        // 测试压缩文件存储
        testCompressedFileStorage();
        
        // 总结
        System.out.println("\n✅ 桥接模式测试通过");
        System.out.println("- 4种格式 × 5种存储 = 20种组合全部测试完成");
        System.out.println("- 额外演示：压缩文件存储（CompressedFileStorage）");
        System.out.println("- 只需 11个类（而非继承的21个类）");
    }
    
    /**
     * 测试所有组合：4种格式 × 5种存储 = 20种
     */
    private static void testAllCombinations() {
        // 创建5种存储实例
        Storage[] storages = {
            new FileStorage("logs/test.log"),
            new ConsoleStorage(),
            new ElasticsearchStorage("localhost:9200", "logs"),
            new KafkaStorage("localhost:9092", "log-topic"),
            new S3Storage("my-bucket", "us-east-1", "logs/")
        };
        
        String[] storageNames = {"File", "Console", "Elasticsearch", "Kafka", "S3"};
        
        // 4种格式
        String[] formats = {"JSON", "XML", "PlainText", "Protobuf"};
        
        int count = 0;
        for (int i = 0; i < formats.length; i++) {
            System.out.println(String.format("【测试%d】%s格式 + 所有存储", i + 1, formats[i]));
            for (int j = 0; j < storages.length; j++) {
                count++;
                String testMessage = String.format("%s-%s组合测试", formats[i], storageNames[j]);
                
                // 根据格式创建对应的LogController
                LogController controller = createController(formats[i], storages[j]);
                controller.process(testMessage);
            }
            System.out.println();
        }
        
        System.out.println(String.format("共测试 %d 种组合", count));
    }
    
    /**
     * 测试压缩文件存储
     * 演示CompressedFileStorage的使用（try-with-resources自动关闭）
     */
    private static void testCompressedFileStorage() {
        System.out.println("【额外演示】压缩文件存储\n");
        
        // 使用try-with-resources自动管理资源
        try (CompressedFileStorage compressedStorage = new CompressedFileStorage("logs/compressed-demo.log")) {
            // 使用所有4种格式，每种写入10条日志
            String[] formats = {"JSON", "XML", "PlainText", "Protobuf"};
            
            for (int i = 0; i < formats.length; i++) {
                System.out.println((i + 1) + ". " + formats[i] + "格式写入压缩文件:");
                
                for (int j = 1; j <= 10; j++) {
                    String testMessage = String.format("压缩测试-%s格式-第%d条日志", formats[i], j);
                    LogController controller = createController(formats[i], compressedStorage);
                    controller.process(testMessage);
                }
                System.out.println();
            }
        } // CompressedFileStorage会自动关闭
        
        System.out.println("💡 提示: 查看压缩文件内容");
        System.out.println("   命令: gunzip -c logs/compressed-demo.log.gz");
        System.out.println("   文件: logs/compressed-demo.log.gz");
        System.out.println("   共写入: 4种格式 × 10条 = 40条日志");
    }
    
    /**
     * 打印分隔符
     */
    private static void printSeparator() {
        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();
    }
    
    /**
     * 工厂方法：根据格式名称创建对应的LogController
     */
    private static LogController createController(String format, Storage storage) {
        switch (format) {
            case "JSON":
                return new JSONLogController(storage);
            case "XML":
                return new XMLLogController(storage);
            case "PlainText":
                return new PlainTextLogController(storage);
            case "Protobuf":
                return new ProtobufLogController(storage);
            default:
                throw new IllegalArgumentException("Unknown format: " + format);
        }
    }
}
