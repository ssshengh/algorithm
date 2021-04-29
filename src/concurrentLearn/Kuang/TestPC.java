package concurrentLearn.Kuang;


//生产者消费者问题，解决办法一，管程法-->缓冲区解决
public class TestPC {
    public static void main(String[] args) {
        buffer buffer = new buffer();

        new conductor(buffer).start();//开启生产者线程
        new consumer(buffer).start();//开启消费者线程

    }
}

class conductor extends Thread{//生产者
    buffer buffer;//二者都需要定义缓冲区
    public conductor(buffer buffer){this.buffer = buffer;}

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("生产了"+i+"只鸡");
            this.buffer.push(new Chicken(i));
        }
    }
}

class consumer extends Thread{//消费者
    buffer buffer;//二者都需要定义缓冲区
    public consumer(buffer buffer){this.buffer = buffer;}

    //消费
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("消费量"+this.buffer.pop().id+"只鸡");

        }
    }
}
//产品
class Chicken{
    int id;
    public Chicken(int id){this.id = id;}
}
//缓冲区：关键！！
class buffer{
    //需要一个容器大小
    Chicken[] chickens = new Chicken[10];
    //容器计数器
    int count = 0;

    //生产者放入产品
    public synchronized void push(Chicken chicken){
        if (count == chickens.length){
            //通知消费者过来取
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //没有满就继续放入
        chickens[count] = chicken;
        count++;
        this.notifyAll();
    }

    //消费者消费：
    public synchronized Chicken pop(){
        if (count==0){
            //等待生产者生产
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //否则消费
        count--;
        Chicken chicken = chickens[count];

        //吃完了，通知生产者生产
        this.notify();

        //通知生产者生产
        return chicken;//并且返回消费的鸡
    }
}

//信号灯法：标识位

class TestPc2{
    public static void main(String[] args) {
        TV tv = new TV();
        new Player(tv).start();
        new Watcher(tv).start();
    }
}

//生产者-->演员
class Player extends Thread{
    TV tv ;
    public Player(TV tv){this.tv = tv;}

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (i%2 == 0){
                this.tv.play("aa");
            }else
                this.tv.play("bb");

        }
    }
}

//消费者-->观众
class Watcher extends Thread{
    TV tv ;
    public Watcher(TV tv){this.tv = tv;}

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            tv.watch();
        }
    }
}

//产品-->节目
class TV {
    String voice;//表演的节目
    boolean flag = true;

    //演员表演的时候，观众等待
    public synchronized void play(String voice){
        if (!flag){
            //等待观众观看
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("演员表演："+ voice);
        //通知观众观看
        this.notify();//唤醒
        this.voice = voice;
        this.flag = !flag;
    }

    //观众观看，演员等待
    public synchronized void watch(){
        if (flag){
            //等待表演
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("观看了："+voice);
        //通知演员表演
        this.notify();
        this.flag = !flag;
    }
}