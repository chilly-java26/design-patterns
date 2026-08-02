package com.designpatterns.iterator.realworld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 具体代码示例：客户端如何被你的改动影响
 */
public class ConcreteExample {
    
    // ========== 你开发的基础库（Version 1.0）==========
    static class BookRepository_V1 {
        private List<String> books = new ArrayList<>();
        
        public BookRepository_V1() {
            books.add("Java编程思想");
            books.add("设计模式");
            books.add("重构");
        }
        
        // 直接暴露 List
        public List<String> getAllBooks() {
            return books;
        }
    }
    
    // ========== 客户端A：前端团队写的代码 ==========
    static class WebController {
        private BookRepository_V1 repository = new BookRepository_V1();
        
        public void showBooksPage() {
            System.out.println("【前端团队的代码】");
            List<String> books = repository.getAllBooks();
            System.out.println("网页展示 " + books.size() + " 本书:");
            for (String book : books) {
                System.out.println("  - " + book);
            }
        }
    }
    
    // ========== 客户端B：报表团队写的代码 ==========
    static class ReportGenerator {
        private BookRepository_V1 repository = new BookRepository_V1();
        
        public void generateReport() {
            System.out.println("\n【报表团队的代码】");
            List<String> books = repository.getAllBooks();
            System.out.println("生成 Excel 报表，共 " + books.size() + " 条数据");
            System.out.println("导出到: /reports/books_" + 
                             java.time.LocalDate.now() + ".xlsx");
        }
    }
    
    // ========== 客户端C：其他同事的代码 ==========
    static class EmailService {
        private BookRepository_V1 repository = new BookRepository_V1();
        
        public void sendNewBooksEmail() {
            System.out.println("\n【邮件服务团队的代码】");
            List<String> books = repository.getAllBooks();
            System.out.println("发送邮件通知: 图书馆新增 " + books.size() + " 本书");
        }
    }
    
    // ========== 6个月后，你想优化性能 ==========
    static class BookRepository_V2 {
        private String[] books;  // 改成数组了！
        private int size;
        
        public BookRepository_V2() {
            books = new String[100];
            books[0] = "Java编程思想";
            books[1] = "设计模式";
            books[2] = "重构";
            size = 3;
        }
        
        // ❌ 返回类型变了！
        public String[] getAllBooks() {
            return books;
        }
        
        public int getSize() {
            return size;
        }
    }
    
    // ========== 现在所有客户端代码都编译失败！==========
    static class WebController_Broken {
        private BookRepository_V2 repository = new BookRepository_V2();
        
        public void showBooksPage() {
            // ❌ 编译错误：Type mismatch
            // List<String> books = repository.getAllBooks();
            
            // 必须改成：
            String[] books = repository.getAllBooks();
            System.out.println("网页展示 " + repository.getSize() + " 本书:");
            for (int i = 0; i < repository.getSize(); i++) {
                if (books[i] != null) {
                    System.out.println("  - " + books[i]);
                }
            }
        }
    }
    
    // ========== 正确做法：使用迭代器 ==========
    static interface BookRepository {
        Iterator<String> iterator();
    }
    
    static class BookRepositoryImpl implements BookRepository {
        private List<String> books = new ArrayList<>();  // 或者用数组，随便改
        
        public BookRepositoryImpl() {
            books.add("Java编程思想");
            books.add("设计模式");
            books.add("重构");
        }
        
        @Override
        public Iterator<String> iterator() {
            return books.iterator();
        }
        
        // 内部随便怎么改都行，客户端不受影响
        public void optimizeInternally() {
            // 比如：改成数组、改成 LinkedList、改成数据库查询
            // 只要 iterator() 方法返回正确的迭代器就行
        }
    }
    
    static class WebController_Correct {
        private BookRepository repository = new BookRepositoryImpl();
        
        public void showBooksPage() {
            System.out.println("\n【使用迭代器的前端代码】");
            Iterator<String> iterator = repository.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                System.out.println("  - " + iterator.next());
                count++;
            }
            System.out.println("总共 " + count + " 本书");
            System.out.println("✓ 无论 BookRepository 内部怎么改，这段代码永远不用动！");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 客户端是谁？怎么会被影响？===\n");
        
        System.out.println("【Version 1.0】所有团队都正常工作：");
        System.out.println("------------------------------------------------------------");
        new WebController().showBooksPage();
        new ReportGenerator().generateReport();
        new EmailService().sendNewBooksEmail();
        
        System.out.println("\n\n【Version 2.0】你改了 BookRepository，结果...");
        System.out.println("------------------------------------------------------------");
        System.out.println("❌ WebController 编译失败");
        System.out.println("❌ ReportGenerator 编译失败");
        System.out.println("❌ EmailService 编译失败");
        System.out.println("❌ 其他 15 个用到 BookRepository 的类都编译失败");
        System.out.println();
        System.out.println("结果：");
        System.out.println("  - 前端团队来找你：\"为什么我的代码突然不能编译了？\"");
        System.out.println("  - 报表团队来找你：\"版本升级后报表模块全挂了！\"");
        System.out.println("  - 老板找你：\"为什么一个优化导致整个项目停工？\"");
        
        System.out.println("\n\n【正确做法】使用迭代器：");
        System.out.println("------------------------------------------------------------");
        new WebController_Correct().showBooksPage();
        System.out.println("\n无论你如何优化 BookRepositoryImpl 的内部实现，");
        System.out.println("所有使用它的代码都不需要改动！");
        
        System.out.println("\n\n=== 总结 ===");
        System.out.println("客户端 = 任何使用你写的类的代码，包括：");
        System.out.println("  1. 你的同事写的代码");
        System.out.println("  2. 其他团队写的代码");
        System.out.println("  3. 使用你开源库的外部开发者");
        System.out.println("  4. 甚至是未来的你自己！");
        System.out.println();
        System.out.println("改动代码 = 编译失败 + 运行时错误 + 大量重构工作");
    }
}
