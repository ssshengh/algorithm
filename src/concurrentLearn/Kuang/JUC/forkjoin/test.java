package concurrentLearn.Kuang.JUC.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 求和计算的任务：
 * 最次：for相加，次优：forkJoin，最优：并行流，更优：具体分析
 */
public class test extends RecursiveTask<Long> {
    private Long start;
    private Long end;

    //临界值：
    private Long temp = 100000L;
    public test(Long start, Long end){this.end = end; this.start = start;}

    @Override
    public Long compute() {
        //需要重写一个计算的方法
        if ((end-start)<temp){
            long sum = 0L;
            for (Long i = start; i < end; i++) {
                sum+=1;
            }
            return sum;
        }else {
            //分支合并计算：forkjoin,其实就是递归
            long middle = (end-start)>>2;
            test test = new test(start, middle);//本身就是一个计算任务了
            test.fork();//把线程压入队列
            test test1 = new test(middle+1, end);//拆为两个任务
            test1.fork();

            //提取结果
            return test.join()+test1.join();
        }

    }
}

class GO{
    public static void main(String[] args) {
        GO go = new GO();
        go.Pstream();

    }
    public void normal(){
        long start = System.currentTimeMillis();
        long sum = 0L;
        for (long i = 0; i < 10_000_0000; i++) {
            sum+=1;
        }
        long end  = System.currentTimeMillis();
        System.out.println("sum = "+sum+" ,时间："+(end-start));
    }

    public void byFJ(){
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();//创建池子
        ForkJoinTask<Long> task = new test(0L, 10_000_0000L);//任务,从递归任务向下转型为FJ任务
        //forkJoinPool.execute(task);//执行任务
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);//提交任务

        try {
            Long sum = submit.get();//获取结果
            long end  = System.currentTimeMillis();
            System.out.println("sum = "+ sum + " ,时间："+(end-start));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void Pstream(){
        //妈的，FOrkJoin把我堆跑溢出了，还是用并行流
        long start = System.currentTimeMillis();

        Long reduce = LongStream.rangeClosed(0L, 10_000_0000L)
                .parallel()
                .reduce(0, Long::sum);
        long end  = System.currentTimeMillis();
        System.out.println("sum = "+reduce+" ,时间："+(end-start));
    }
}