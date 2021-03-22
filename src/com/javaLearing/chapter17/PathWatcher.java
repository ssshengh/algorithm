package com.javaLearing.chapter17;

// files/PathWatcher.java
// {ExcludeFromGradle}
import java.io.IOException;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.concurrent.*;

public class PathWatcher {
    static Path test = Paths.get("test");

    static void delTxtFiles() {
        try {
            Files.walk(test)
                    .filter(f ->
                            f.toString()
                                    .endsWith(".txt"))
                    .forEach(f -> {
                        try {
                            System.out.println("deleting " + f);
                            Files.delete(f);
                        } catch(IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            //delTxtFiles() 中的 try 代码块看起来有些多余，因为它们捕获的是同一种类型的异常，外部的 try 语句似乎已经足够了。
            // 然而出于某种原因，Java 要求两者都必须存在(这也可能是一个 bug)。
            // 还要注意的是在 filter() 中，我们必须显式地使用 f.toString() 转为字符串，
            // 否则我们调用 endsWith() 将会与整个 Path 对象进行比较，而不是路径名称字符串的一部分进行比较。
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Directories.refreshTestDir();
        Directories.populateTestDir();
        Files.createFile(test.resolve("Hello.txt"));
        //一旦我们从 FileSystem 中得到了 WatchService 对象，我们将其注册到 test 路径以及我们感兴趣的项目的变量参数列表中，
        // 可以选择 ENTRY_CREATE，ENTRY_DELETE 或 ENTRY_MODIFY(其中创建和删除不属于修改)。
        /**
         * 因为接下来对 watcher.take() 的调用会在发生某些事情之前停止所有操作，
         * 所以我们希望 deltxtfiles() 能够并行运行以便生成我们感兴趣的事件。
         * 为了实现这个目的，我通过调用 Executors.newSingleThreadScheduledExecutor() 产生一个 ScheduledExecutorService 对象，
         * 然后调用 schedule() 方法传递所需函数的方法引用，并且设置在运行之前应该等待的时间。
         *
         * 此时，watcher.take() 将等待并阻塞在这里。当目标事件发生时，会返回一个包含 WatchEvent 的 Watchkey 对象。
         * 展示的这三种方法是能对 WatchEvent 执行的全部操作。
         *
         * 查看输出的具体内容。即使我们正在删除以 .txt 结尾的文件，在 Hello.txt 被删除之前，WatchService 也不会被触发。
         * 你可能认为，如果说"监视这个目录"，自然会包含整个目录和下面子目录，但实际上：只会监视给定的目录，
         * 而不是下面的所有内容。如果需要监视整个树目录，必须在整个树的每个子目录上放置一个 Watchservice。
         * ：TreeWatcher
         * */

        WatchService watcher = FileSystems.getDefault().newWatchService();
        test.register(watcher, ENTRY_DELETE);
        Executors.newSingleThreadScheduledExecutor()
                .schedule(PathWatcher::delTxtFiles,
                        250, TimeUnit.MILLISECONDS);
        WatchKey key = watcher.take();
        for(WatchEvent evt : key.pollEvents()) {
            System.out.println("evt.context(): " + evt.context() +
                    "\nevt.count(): " + evt.count() +
                    "\nevt.kind(): " + evt.kind());
            System.exit(0);
        }
    }
}
/* Output:
deleting test\bag\foo\bar\baz\File.txt
deleting test\bar\baz\bag\foo\File.txt
deleting test\baz\bag\foo\bar\File.txt
deleting test\foo\bar\baz\bag\File.txt
deleting test\Hello.txt
evt.context(): Hello.txt
evt.count(): 1
evt.kind(): ENTRY_DELETE
*/

