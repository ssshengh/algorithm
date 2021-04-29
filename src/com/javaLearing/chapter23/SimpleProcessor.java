package com.javaLearing.chapter23;

// annotations/simplest/SimpleProcessor.java
// A bare-bones annotation processor
//package annotations.simplest;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.*;

//（旧的，失效的）apt 版本的处理器需要额外的方法来确定支持哪些注解以及支持的 Java 版本。
// 不过，你现在可以简单的使用 @SupportedAnnotationTypes 和 @SupportedSourceVersion 注解（这是一个很好的用注解简化代码的示例）
@SupportedAnnotationTypes(
        "annotations.simplest.Simple")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SimpleProcessor
        extends AbstractProcessor {

    /**
     * 你唯一需要实现的方法就是 process()，这里是所有行为发生的地方。
     * 第一个参数告诉你哪个注解是存在的，第二个参数保留了剩余信息。
     * 我们所做的事情只是打印了注解（这里只存在一个），可以看 TypeElement 文档中的其他行为。
     * 通过使用 process() 的第二个操作，我们循环所有被 @Simple 注解的元素，并且针对每一个元素调用我们的 display() 方法。
     * 所有 Element 展示了自身的基本信息；例如，getModifiers() 告诉你它是否为 public 和 static 。
     * @param annotations : 第一个参数告诉你哪个注解是存在的
     * @param env : 第二个参数保留了剩余信息
     * @return
     */
    @Override
    public boolean process(
            Set<? extends TypeElement> annotations,
            RoundEnvironment env) {
        for(TypeElement t : annotations)
            System.out.println(t);
        for(Element el :
                env.getElementsAnnotatedWith(Simple.class))
            display(el);
        return false;
    }

    /**
     * Element 只能执行那些编译器解析的所有基本对象共有的操作，而类和方法之类的东西有额外的信息需要提取。
     * 所以（如果你阅读了正确的文档，但是我没有在任何文档中找到——我不得不通过 StackOverflow 寻找线索）你检查它是哪种 ElementKind，
     * 然后将其向下转换为更具体的元素类型，注入针对 CLASS 的 TypeElement 和针对 METHOD 的ExecutableElement。此时，可以为这些元素调用其他方法。
     * */
    private void display(Element el) {
        System.out.println("==== " + el + " ====");
        System.out.println(el.getKind() +
                " : " + el.getModifiers() +
                " : " + el.getSimpleName() +
                " : " + el.asType());
        if(el.getKind().equals(ElementKind.CLASS)) {
            TypeElement te = (TypeElement)el;
            System.out.println(te.getQualifiedName());
            System.out.println(te.getSuperclass());
            System.out.println(te.getEnclosedElements());
        }
        if(el.getKind().equals(ElementKind.METHOD)) {
            ExecutableElement ex = (ExecutableElement)el;
            System.out.print(ex.getReturnType() + " ");
            System.out.print(ex.getSimpleName() + "(");
            System.out.println(ex.getParameters() + ")");
        }
    }
}

