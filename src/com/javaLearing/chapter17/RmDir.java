package com.javaLearing.chapter17;

// onjava/RmDir.java
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class RmDir {
    public static void rmdir(Path dir) throws IOException {
        /**
         * 删除目录树的方法实现依赖于 Files.walkFileTree()*，"walking" 目录树意味着遍历每个子目录和文件。
         * Visitor* 设计模式提供了一种标准机制来访问集合中的每个对象，然后你需要提供在每个对象上执行的操作。
         * 此操作的定义取决于实现的 **FileVisitor 的四个抽象方法，包括：
         *
         * 1.  **preVisitDirectory()**：在访问目录中条目之前在目录上运行。
         * 2.  **visitFile()**：运行目录中的每一个文件。
         * 3.  **visitFileFailed()**：调用无法访问的文件。
         * 4.  **postVisitDirectory()**：在访问目录中条目之后在目录上运行，包括所有的子目录。
         * */
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
//为了简化，java.nio.file.SimpleFileVisitor 提供了所有方法的默认实现。
// 这样，在我们的匿名内部类中，我们只需要重写非标准行为的方法：visitFile() 和 postVisitDirectory() 实现删除文件和删除目录。
// 两者都应该返回标志位决定是否继续访问(这样就可以继续访问，直到找到所需要的)。
// 作为探索目录操作的一部分，现在我们可以有条件地删除已存在的目录。
// 在以下例子中，makeVariant() 接受基本目录测试，并通过旋转部件列表生成不同的子目录路径。
// 这些旋转与路径分隔符 sep 使用 String.join() 贴在一起，然后返回一个 Path 对象。
