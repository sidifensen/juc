import java.util.concurrent.locks.LockSupport;

public class Test12 {

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {

        ParkUnpark parkUnpark = new ParkUnpark(5);
        t1 = new Thread(() -> {
                parkUnpark.print("a",t2);
        });
        t2 = new Thread(() -> {
                parkUnpark.print("b",t3);
        });
        t3 = new Thread(() -> {
                parkUnpark.print("c",t1);
        });

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);//先唤醒t1
    }

}

class ParkUnpark {

    private int loopNumber;

    public ParkUnpark(int loopNumber){
        this.loopNumber = loopNumber;
    }

    public void print(String str,Thread next){
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();//阻塞当前线程
            System.out.print(str);
            LockSupport.unpark(next);//唤醒next线程
        }
    }

}
