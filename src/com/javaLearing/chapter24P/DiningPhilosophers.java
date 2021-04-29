package com.javaLearing.chapter24P;

import com.javaLearing.chapter19.A;
import com.javaLearing.chapter24.Nap;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;


/**
 * 当你停止查看输出时，该程序将死锁。但是，根据你的计算机配置，你可能不会看到死锁。看来这取决于计算机上的内核数[^7]。两个核心不会产生死锁，但两核以上却很容易产生死锁。
 * 此行为使该示例更好地说明了死锁，因为你可能正在具有 2 核的计算机上编写程序（如果确实是导致问题的原因），并且确信该程序可以正常工作，只能启动它将其安装在另一台计算机上时出现死锁。请注意，不能因为你没或不容易看到死锁，这并不意味着此程序不会在 2 核机器上发生死锁。 该程序仍然有死锁倾向，只是很少发生——可以说是最糟糕的情况，因为问题不容易出现。
 */
public class DiningPhilosophers {
    private StickHolder[] sticks;
    private Philosopher[] philosophers;

    public DiningPhilosophers(int n){
        sticks = new StickHolder[n];//n根筷子，n个哲学家
        Arrays.setAll(sticks, i->new StickHolder());

        philosophers = new Philosopher[n];//n个哲学家
        Arrays.setAll(philosophers,
                i->new Philosopher(i, sticks[i], sticks[(i + 1) % n]));//注意这个循环取法

        // Fix by reversing stick order for this one:
        // philosophers[1] =                     // [2]
        //   new Philosopher(0, sticks[0], sticks[1]);

        Arrays.stream(philosophers).forEach(CompletableFuture::runAsync);//每个并行执行run()
    }

    public static void main(String[] args) {
        // Returns right away:
        new DiningPhilosophers(5);               // [4]
        // Keeps main() from exiting:
        new Nap(3, "Shutdown");
    }
}
/**
 * 在 DiningPhilosophers 的构造方法中，每个哲学家都获得一个左右筷子的引用。除最后一个哲学家外，都是通过把哲学家放在下一双空闲筷子之间来初始化：
 *      最后一位哲学家得到了第 0 根筷子作为他的右筷子，所以圆桌就完成。
 *      那是因为最后一位哲学家正坐在第一个哲学家的旁边，而且他们俩都共用零筷子。[1] 显示了以 n 为模数选择的右筷子，将最后一个哲学家绕到第一个哲学家的旁边。
 *
 * 现在，所有哲学家都可以尝试吃饭，每个哲学家都在旁边等待哲学家放下筷子。
 *      为了让每个哲学家在[3] 上运行，调用 runAsync()，这意味着 DiningPhilosophers 的构造函数立即返回到[4]。
 *      如果没有任何东西阻止 main() 完成，程序就会退出，不会做太多事情。
 *      Nap 对象阻止 main() 退出，然后在三秒后强制退出 (假设/可能是) 死锁程序。
 *
 * 在给定的配置中，哲学家几乎不花时间思考。因此，他们在吃东西的时候都争着用筷子，而且往往很快就会陷入僵局。你可以改变这个:
 * 通过增加[4] 的值来添加更多哲学家。
 *
 * 在 Philosopher.java 中取消注释行[1]。
 *
 * 任一种方法都会减少死锁的可能性，这表明编写并发程序并认为它是安全的危险，因为它似乎“在我的机器上运行正常”。
 * 你可以轻松地说服自己该程序没有死锁，即使它不是。这个示例相当有趣，因为它演示了看起来可以正确运行，但实际上会可能发生死锁的程序。
 * */