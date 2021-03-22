package com.javaLearing.chapter19;

// typeinfo/RegisteredFactories.java
// 注册工厂到基础类
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

//工厂方法设计模式：为了解决每次添加一个新的Part类型时，向每个子类添加静态初始值设定项，因此初始值设定项会将其类添加到某个列表中
//而这种情况存在问题
/**
 * 我们在这里所做的另一个更改是使用工厂方法设计模式将对象的创建推迟到类本身。工厂方法可以以多态方式调用，并为你创建适当类型的对象。
 * 事实证明，java.util.function.Supplier 用 T get() 描述了原型工厂方法。协变返回类型允许 get() 为 Supplier 的每个子类实现返回不同的类型。
 * */
class Part implements Supplier<Part> {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    static List<Supplier<? extends Part>> prototypes =
            Arrays.asList(
                    new FuelFilter(),
                    new AirFilter(),
                    new CabinAirFilter(),
                    new OilFilter(),
                    new FanBelt(),
                    new PowerSteeringBelt(),
                    new GeneratorBelt()
            );
    //并非层次结构中的所有类都应实例化；这里的 Filter 和 Belt 只是分类器，这样你就不会创建任何一个类的实例，
    // 而是只创建它们的子类（请注意，如果尝试这样做，你将获得 Part 基类的行为）。

    private static Random rand = new Random(47);
    public Part get() {
        int n = rand.nextInt(prototypes.size());
        return prototypes.get(n).get();
    }//思路为利用Supplier的get方法，随意在这里创建一个子类
}

/**
 * 因为 Part implements Supplier<Part>，Part 通过其 get() 方法供应其他 Part。
 * 如果为基类 Part 调用 get()（或者如果 generate() 调用 get()），它将创建随机特定的 Part 子类型，每个子类型最终都从 Part 继承，
 * 并重写相应的 get() 以生成它们中的一个。
 * */
class Filter extends Part {}

class FuelFilter extends Filter {
    @Override
    public FuelFilter get() {
        return new FuelFilter();
    }
}

class AirFilter extends Filter {
    @Override
    public AirFilter get() {
        return new AirFilter();
    }
}

class CabinAirFilter extends Filter {
    @Override
    public CabinAirFilter get() {
        return new CabinAirFilter();
    }
}

class OilFilter extends Filter {
    @Override
    public OilFilter get() {
        return new OilFilter();
    }
}

class Belt extends Part {}

class FanBelt extends Belt {
    @Override
    public FanBelt get() {
        return new FanBelt();
    }
}

class GeneratorBelt extends Belt {
    @Override
    public GeneratorBelt get() {
        return new GeneratorBelt();
    }
}

class PowerSteeringBelt extends Belt {
    @Override
    public PowerSteeringBelt get() {
        return new PowerSteeringBelt();
    }
}

public class RegisteredFactories {
    public static void main(String[] args) {
        Stream.generate(new Part())
                .limit(10)
                .forEach(System.out::println);
    }
}

