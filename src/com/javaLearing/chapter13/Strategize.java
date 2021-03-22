package com.javaLearing.chapter13;

// functional/Strategize.java

interface Strategy {
    String approach(String msg);
}

class Soft implements Strategy {
    public String approach(String msg) {
        return msg.toLowerCase() + "?";
    }
}

class Unrelated {
    static String twice(String msg) {
        return msg + " " + msg;
    }
}

public class Strategize {
    Strategy strategy;
    String msg;

    Strategize(String msg) {
        strategy = new Soft(); // [1]在 Strategize 中，你可以看到 Soft 作为默认策略，在构造函数中赋值。
        this.msg = msg;
    }

    public static void main(String[] args) {
        //创建三个Strategy对象，组成一个引用数组。里面包含的是三种方法，都是传入一个参数msg，并进行改变
        Strategy[] strategies = {
                new Strategy() { // [2] 一种较为简洁且更加自然的方法是创建一个匿名内部类。即便如此，仍有相当数量的冗余代码。你总需要仔细观察后才会发现：“哦，我明白了，原来这里使用了匿名内部类。”
                    public String approach(String msg) {
                        return msg.toUpperCase() + "!";
                    }
                },
                msg -> msg.substring(0, 5),
                // [3]Java 8 的 Lambda 表达式，其参数和函数体被箭头 -> 分隔开。
                // 箭头右侧是从 Lambda 返回的表达式。它与单独定义类和采用匿名内部类是等价的，但代码少得多。
                Unrelated::twice
                // [4]Java 8 的方法引用，它以 :: 为特征。 :: 的左边是类或对象的名称， :: 的右边是方法的名称，但是没有参数列表
        };
        Strategize s = new Strategize("Hello there");//对象里面包含了接口对象
        s.communicate();
        for(Strategy newStrategy : strategies) {
            s.changeStrategy(newStrategy);//修改传入的接口对象修改方法
            // [5]在使用默认的 Soft 策略之后，我们逐步遍历数组中的所有 Strategy，并通过调用 changeStrategy() 方法将每个 Strategy 传入变量 s 中。
            s.communicate();
            // [6]现在，每次调用 communicate() 都会产生不同的行为，具体取决于此刻正在使用的策略代码对象。我们传递的是行为，而并不仅仅是数据。[^3]
        }
    }

    void communicate() {
        System.out.println(strategy.approach(msg));
    }

    void changeStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}

