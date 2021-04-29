package concurrentLearn.Kuang;

import java.sql.Struct;

//龟兔赛跑，使用两个线程来模拟，看谁先跑完
public class GuiTuRUN implements Runnable{
    private static String  winner;//胜利,只能被定义一次

    @Override
    public void run() {
        for (int i = 0; i<=100; i++){
            //大家一起跑100步
            if (Thread.currentThread().getName().equals("兔子"))//兔子睡一会
            {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }

            if (gameOver(i))
                break;//出现胜利者停止
            if (i%5 == 0)//每5步打印一次
                System.out.println(
                        Thread.currentThread().getName()+"-->正在跑第："+i+"步"
                );

        }
    }
    private boolean gameOver(int steps){
        if (winner != null)
            return true;
        {
            if (steps  == 100){
                winner = Thread.currentThread().getName();
                System.out.println("胜利者是："+winner);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        GuiTuRUN race = new GuiTuRUN();//一条赛道
        new Thread(race, "兔子").start();
        new Thread(race, "乌龟").start();
    }
}
