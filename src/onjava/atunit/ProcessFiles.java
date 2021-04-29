package onjava.atunit;

// onjava/ProcessFiles.java
import java.io.*;
import java.nio.file.*;
public class ProcessFiles {

    public interface Strategy {
        void process(File file);
    }

    private Strategy strategy;
    private String ext;

    public ProcessFiles(Strategy strategy, String ext) {
        this.strategy = strategy;
        this.ext = ext;
    }

    public void start(String[] args) {
        try {
            if(args.length == 0)
                processDirectoryTree(new File("."));
            else
                for(String arg : args) {
                    File fileArg = new File(arg);
                    if(fileArg.isDirectory())
                        processDirectoryTree(fileArg);
                    else {
// Allow user to leave off extension:
                        if(!arg.endsWith("." + ext))
                            arg += "." + ext;
                        strategy.process(
                                new File(arg).getCanonicalFile());
                    }
                }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void processDirectoryTree(File root) throws IOException {
        PathMatcher matcher = FileSystems.getDefault()
                .getPathMatcher("glob:**/*.{" + ext + "}");
        Files.walk(root.toPath())
                .filter(matcher::matches)
                .forEach(p -> strategy.process(p.toFile()));
    }
}

//AtUnit 类实现了 ProcessFiles.Strategy，其包含了一个 process() 方法。
// 在这种方式下，AtUnit 实例可以作为参数传递给 ProcessFiles 构造器。第二个构造器的参数告诉 ProcessFiles 如寻找所有包含 “class” 拓展名的文件。
class DemoProcessFiles {
    public static void main(String[] args) {
        new ProcessFiles(file -> System.out.println(file),
                "java").start(args);
    }
}