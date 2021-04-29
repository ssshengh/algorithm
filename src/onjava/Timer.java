package onjava;


import static java.util.concurrent.TimeUnit.*;

public class Timer {
    private long start = System.nanoTime();//以纳秒为内返回运行中的 Java 虚拟机高分辨率时间源的当前值。此方法只能用于测量已过的时间，与任何其他系统或时钟时间概念无关。
    public long duration() {
        return NANOSECONDS.toMillis(
                System.nanoTime() - start);
    }
    public static long duration(Runnable test) {
        Timer timer = new Timer();
        test.run();
        return timer.duration();
    }

    public static void main(String[] args) {
        System.out.println((long)784*300*124*60*10);

    }
}
