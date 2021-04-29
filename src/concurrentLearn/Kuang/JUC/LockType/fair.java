package concurrentLearn.Kuang.JUC.LockType;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class fair {
    Lock lock = new ReentrantLock();//非公平锁
    Lock fair = new ReentrantLock(true);//公平锁
}
