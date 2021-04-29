package concurrentLearn.Kuang.JUC.Singal;

/**
 * 单例模式的关键：构造器私有
 * <ul>
 * 饿汉式单例模式：很饿，一上来就可以接受，所以不管如何，饿汉式单例上来就先new一个本身的对象
 * </ul>
 */
public class hungry {
    private static final hungry HUNGRY = new hungry();//不管如何先new一个
    private hungry(){}//构造器私有
    //通过这个函数得到对象，静态可以保证一定的可见性，再额外的内存区域中
    public static hungry getHungry(){
        return HUNGRY;
    }

    //饿汉式的问题，在加载类的时候:这些也会被一起加载，尽管不一定会被用到，造成了内存浪费
    //查阅JVM，注意到final变量是在类加载的准备阶段会初始化为一个值，其他的不管是int还是对象还是加了static都被赋予默认值
    //等到类加载的初始化阶段初始化
    byte[] bytes = new byte[1024*1024];
    byte[] bytes1 = new byte[1024*1024];
    byte[] bytes3 = new byte[1024*1024];
    byte[] bytes2 = new byte[1024*1024];
}
