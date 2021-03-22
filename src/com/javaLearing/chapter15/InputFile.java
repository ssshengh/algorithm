package com.javaLearing.chapter15;

// exceptions/InputFile.java
// Paying attention to exceptions in constructors
import java.io.*;
public class InputFile {

    private BufferedReader in;

    //构造器
    public InputFile(String fname) throws Exception {
        /**
         * InputFile 的构造器接受字符串作为参数，该字符串表示所要打开的文件名。在 try 块中，会使用此文件名建立 FileReader 对象。
         * FileReader 对象本身用处并不大，但可以用它来建立 BufferedReader 对象。注意，使用 InputFile 的好处之一是把两步操作合而为一。
         * */
        try {
            in = new BufferedReader(new FileReader(fname));
            // Other code that might throw exceptions
        } catch(FileNotFoundException e) {
            //如果 FileReader 的构造器失败了，将抛出 FileNotFoundException 异常。对于这个异常，并不需要关闭文件，因为这个文件还没有被打开
            System.out.println("Could not open " + fname);
            // Wasn't open, so don't close it
            throw e;
        } catch(Exception e) {
            /**
             * 而任何其他捕获异常的 catch 子句必须关闭文件，因为在它们捕获到异常之时，文件已经打开了（
             * 当然，如果还有其他方法能抛出 FileNotFoundException，这个方法就显得有些投机取巧了。
             * 这时，通常必须把这些方法分别放到各自的 try 块里），close() 方法也可能会抛出异常，
             * 所以尽管它已经在另一个 catch 子句块里了，还是要再用一层 try-catch，这对 Java 编译器而言只不过是多了一对花括号。
             * 在本地做完处理之后，异常被重新抛出，对于构造器而言这么做是很合适的，因为你总不希望去误导调用方，让他认为“这个对象已经创建完毕，可以使用了”。
             * */
            // All other exceptions must close it
            try {
                in.close();
            } catch(IOException e2) {
                System.out.println("in.close() unsuccessful");
            }
            throw e; // Rethrow
        } finally {
            // Don't close it here!!!
            //在本例中，由于 finally 会在每次完成构造器之后都执行一遍，因此它实在不该是调用 close() 关闭文件的地方。
            // 我们希望文件在 InputFlle 对象的整个生命周期内都处于打开状态。
        }
    }



    /**
     * getLine() 方法会返回表示文件下一行内容的字符串。
     * 它调用了能抛出异常的 readLine()，但是这个异常已经在方法内得到处理，因此 getLine() 不会抛出任何异常。
     *
     * 在设计异常时有一个问题：应该把异常全部放在这一层处理；还是先处理一部分，然后再向上层抛出相同的（或新的）异常；
     * 又或者是不做任何处理直接向上层抛出。如果用法恰当的话，直接向上层抛出的确能简化编程。
     * 在这里，getLine() 方法将异常转换为 RuntimeException，表示一个编程错误。
     * */
    public String getLine() {
        String s;
        try {
            s = in.readLine();
        } catch(IOException e) {
            throw new RuntimeException("readLine() failed");
        }
        return s;
    }
    /**
     * 用户在不再需要 InputFile 对象时，就必须调用 dispose() 方法，
     * 这将释放 BufferedReader 和/或 FileReader 对象所占用的系统资源（比如文件句柄），在使用完 InputFile 对象之前是不会调用它的。
     *
     * 可能你会考虑把上述功能放到 finalize() 里面，但我在 封装 讲过，你不知道 finalize() 会不会被调用
     * （即使能确定它将被调用，也不知道在什么时候调用），
     * 这也是 Java 的缺陷：除了内存的清理之外，所有的清理都不会自动发生。所以必须告诉客户端程序员，这是他们的责任。
     * */
    public void dispose() {
        try {
            in.close();
            System.out.println("dispose() successful");
        } catch(IOException e2) {
            throw new RuntimeException("in.close() failed");
        }
    }
}
