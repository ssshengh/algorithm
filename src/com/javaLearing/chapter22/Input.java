package com.javaLearing.chapter22;

import java.util.Random;

public enum Input {
    NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100),
    TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),//这些需要有价格等

    /**
     * 除了两个特殊的 Input 实例之外，其他的 Input 都有相应的价格，因此在接口中定义了 amount方法。
     * 然而，对那两个特殊 Input 实例而言，调用 amount方法并不合适，所以如果程序员调用它们的 amount方法就会有异常抛出
     * （在接口内定义了一个方法，然后在你调用该方法的某个实现时就会抛出异常），这似乎有点奇怪，但由于 enum 的限制，我们不得不采用这种方式。
     * */
    ABORT_TRANSACTION {//这些不需要，所以一定需要一个默认构造器
        @Override
        public int amount() { // Disallow
            throw new RuntimeException("ABORT.amount()");
        }
    },
    STOP { // This must be the last instance.
        @Override
        public int amount() { // Disallow
            throw new RuntimeException("SHUT_DOWN.amount()");
        }
    };

    int value; // In cents
    Input(int value) { this.value = value; }
    Input() {}
    int amount() { return value; }; // In cents
    static Random rand = new Random(47);
    public static Input randomSelection() {
        // Don't include STOP:
        return values()[rand.nextInt(values().length - 1)];
    }
}
