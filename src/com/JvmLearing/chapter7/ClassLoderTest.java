package com.JvmLearing.chapter7;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoderTest {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader classLoader = new ClassLoader(){
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String filieName = name.substring(name.lastIndexOf(".")+1)+".class";//获取类名称
                    InputStream is = getClass().getResourceAsStream(filieName);//获取二进制流
                    if (is == null)
                        return super.loadClass(name);
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);//返回加载的字节码
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = classLoader.loadClass("com.JvmLearing.chapter7.ClassLoderTest")
                .newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof  com.JvmLearing.chapter7.ClassLoderTest);
    }
}
