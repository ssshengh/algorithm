package concurrentLearn.Kuang.JUC.future;

import java.util.concurrent.*;

/**
 * 异步调用：发起请求之后不一定立刻获取结果，只需要执行其他的等待后面将结果发送回来
 * 异步执行、成功回调、失败回调
 */
public class demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //发起一个请求：不要求返回的话
        CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" runAsync=>Void");
        });
        //理解异步回调的关键来了：不管是放入另一个线程还是服务端，关键是一样的，异步的线程在另一个地方执行了，我这里不再阻塞它
        System.out.println("1111");//所以这里就会直接执行打印出来
        future.get();//在获取的时候，就可以注意到，这里是会阻塞的，因为我想在这个位置拿到结果，而在另一个地方未必执行完了！！！

        //有返回值的异步回调：
        //ajax有成功的回调也有失败的回调
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+" supplyAsync=>Integer");
            //int a = 10/0;//加上这一句一定报错，可以看信息
            return 1024;
        });
        System.out.println(future1.whenComplete((t, u) -> {
            System.out.println("T:" + t);//正常的返回结果
            System.out.println("U:" + u);//出错时候的错误信息
        }).exceptionally(throwable -> {
            System.out.println(throwable.getMessage());
            return 2333;
        }).get());
    }
}
