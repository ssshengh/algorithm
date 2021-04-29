package concurrentLearn.Kuang.JUC.FunctionInterface;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Consumer消费型接口，supplier供给型接口
 * <P>
 *     前者源码：public interface Consumer<T>{} ->
 *             void accept(T t);
 * </P>
 * <P>
 *     后者源码:public interface Supplier<T>{} ->
 *             T get();
 * </P>
 *
 */
public class Demo {
    static final int id = 10;
    String test(){
        return id + this.toString();
    }

    void consumerTT(String ss){
        System.out.println(ss+this.toString());
    }
    public static void main(String[] args) {
        Consumer<String > consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("消费了T！");
            }
        };
        Supplier<String> stringSupplier = new Supplier<String>() {
            @Override
            public String get() {
                return "思思大笨蛋！";
            }
        };
        Demo demo = new Demo();
        Supplier<String> stringSupplier1 = demo::test;//同样函数标签的方法赋值
        Consumer<String> stringConsumer = demo::consumerTT;

        System.out.println(stringSupplier1);
        stringConsumer.accept("SS");


    }
}
