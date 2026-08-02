package com.designpatterns.iterator.realworld;

import java.util.ArrayList;
import java.util.List;

/**
 * 真实场景：你在开发一个图书管理系统的基础库
 */
public class RealWorldScenario {
    
    public static void main(String[] args) {
        System.out.println("=== 真实场景：客户端是谁？===\n");
        
        scenario1_YouAreLibraryDeveloper();
        System.out.println("\n============================================================\n");
        scenario2_YourTeammateIsClient();
        System.out.println("\n============================================================\n");
        scenario3_ThirdPartyDevelopers();
    }
    
    /**
     * 场景1: 你开发基础库，其他程序员使用你的库
     */
    static void scenario1_YouAreLibraryDeveloper() {
        System.out.println("【场景1】你是图书馆管理系统的基础库开发者");
        System.out.println();
        
        System.out.println("你写的代码（library-core.jar）:");
        System.out.println("├── BookRepository.java");
        System.out.println("│   └── public List<Book> getAllBooks()  // ⚠️ 暴露了 List");
        System.out.println("└── Book.java");
        System.out.println();
        
        System.out.println("客户端A（前端团队）写的代码:");
        System.out.println("```java");
        System.out.println("List<Book> books = repository.getAllBooks();");
        System.out.println("for (Book book : books) {");
        System.out.println("    displayOnWebPage(book);");
        System.out.println("}");
        System.out.println("```");
        System.out.println();
        
        System.out.println("客户端B（报表团队）写的代码:");
        System.out.println("```java");
        System.out.println("List<Book> books = repository.getAllBooks();");
        System.out.println("generateExcelReport(books);");
        System.out.println("```");
        System.out.println();
        
        System.out.println("客户端C（移动端团队）写的代码:");
        System.out.println("```java");
        System.out.println("List<Book> books = repository.getAllBooks();");
        System.out.println("sendToMobileApp(books);");
        System.out.println("```");
        System.out.println();
        
        System.out.println("6个月后，你发现性能问题，想把 List 改成数组...");
        System.out.println();
        System.out.println("❌ 问题来了：");
        System.out.println("  - 前端团队的 3 个页面代码要改");
        System.out.println("  - 报表团队的 5 个报表代码要改");
        System.out.println("  - 移动端团队的 10 个接口要改");
        System.out.println("  - 总共影响 18 处代码！");
        System.out.println();
        System.out.println("✓ 如果用迭代器：");
        System.out.println("  - 你只需修改 BookRepository 内部实现");
        System.out.println("  - 其他团队的代码 0 处修改！");
    }
    
    /**
     * 场景2: 你的同事就是客户端
     */
    static void scenario2_YourTeammateIsClient() {
        System.out.println("【场景2】同一个项目，不同的人在不同模块工作");
        System.out.println();
        
        System.out.println("你负责: 图书管理模块");
        System.out.println("├── BookShelf.java  // 你写的");
        System.out.println("└── Book.java");
        System.out.println();
        
        System.out.println("你的同事负责:");
        System.out.println("├── OrderService.java     // 小王写的，用到 BookShelf");
        System.out.println("├── ReportService.java    // 小李写的，用到 BookShelf");
        System.out.println("├── SearchService.java    // 小张写的，用到 BookShelf");
        System.out.println("└── NotificationService   // 小赵写的，用到 BookShelf");
        System.out.println();
        
        System.out.println("一个月后，你想优化 BookShelf 的内部实现...");
        System.out.println();
        
        System.out.println("如果你暴露了 List<Book>:");
        System.out.println("❌ 你要通知小王、小李、小张、小赵");
        System.out.println("❌ 他们要检查自己的代码是否受影响");
        System.out.println("❌ 可能引入 bug");
        System.out.println("❌ 需要重新测试所有相关功能");
        System.out.println();
        
        System.out.println("如果你用了迭代器:");
        System.out.println("✓ 你悄悄改，别人无感知");
        System.out.println("✓ 不需要开会讨论");
        System.out.println("✓ 降低团队协作成本");
    }
    
    /**
     * 场景3: 开源库的真实案例
     */
    static void scenario3_ThirdPartyDevelopers() {
        System.out.println("【场景3】开源库/第三方库的情况");
        System.out.println();
        
        System.out.println("假设你开发了一个开源的图书管理库，有 1000 个项目在用");
        System.out.println();
        
        System.out.println("版本 1.0:");
        System.out.println("public List<Book> getBooks()  // 返回 List");
        System.out.println();
        System.out.println("1000 个项目都这样用:");
        System.out.println("for (Book book : library.getBooks()) { ... }");
        System.out.println();
        
        System.out.println("版本 2.0 你想改成数组:");
        System.out.println("public Book[] getBooks()  // 返回数组");
        System.out.println();
        
        System.out.println("❌ 后果：");
        System.out.println("  - 这是 Breaking Change（破坏性变更）");
        System.out.println("  - 1000 个项目全部编译失败！");
        System.out.println("  - GitHub Issue 爆炸");
        System.out.println("  - 你的库被骂惨了");
        System.out.println();
        
        System.out.println("真实案例：Java ArrayList");
        System.out.println("  - JDK 1.2 引入时就用了迭代器模式");
        System.out.println("  - 内部实现改了无数次，但 Iterator 接口从未变过");
        System.out.println("  - 几十年前的代码现在依然能运行！");
    }
}
