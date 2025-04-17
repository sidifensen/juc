import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test11 {
    public static void main(String[] args) throws InterruptedException {


        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();

        new Thread(() -> {
            try {
                awaitSignal.print("a",a,b);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                awaitSignal.print("b",b,c);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                awaitSignal.print("c",c,a);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Thread.sleep(1000l);

        awaitSignal.lock();//加锁
        try {
            a.signal();//程序开始时,先唤醒a线程
        }finally {
            awaitSignal.unlock();
        }

    }
}

class AwaitSignal extends ReentrantLock{
    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    //参数一,打印店内容,参数二,进入哪一间休息室等待
    public void print(String str , Condition current,Condition next) throws InterruptedException {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try{
                current.await();//等待进入休息室
                System.out.println(str + "进入休息室" + i);
                next.signal();//唤醒下一间休息室
            } finally {
                unlock();
            }
            
        }
    }

}

