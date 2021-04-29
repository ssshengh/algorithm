package concurrentLearn.Kuang.JUC.Singal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//枚举本身也是一个类
public enum EnumSingle {
    INSTANCE;//单例
    public EnumSingle getInstance(){return INSTANCE;}
}

class test{
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        EnumSingle single = EnumSingle.INSTANCE;
        Constructor<EnumSingle> singleConstructor = EnumSingle.class.getDeclaredConstructor(String.class, int.class);
        singleConstructor.setAccessible(true);
        singleConstructor.newInstance("ss", 90);

        //java.lang.NoSuchMethodException: concurrentLearn.Kuang.JUC.Singal.EnumSingle.<init>()
        System.out.println(single);
        System.out.println(singleConstructor);
    }
}