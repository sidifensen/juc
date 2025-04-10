import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Test13 {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            log.info("t1 start");

            //LockSupport.park() 是 Java 中的一个方法，用于使当前线程暂停执行，进入等待状态
            LockSupport.park();

//            log.info("打断状态" + Thread.currentThread().isInterrupted());


            //已经把打断状态清除变成false了,程序到最后一个park就卡住了,不会打印end
            log.info("打断状态"   + Thread.interrupted());

            LockSupport.park();
            log.info("t1 end");//还会打印一下
        });

        t1.start();

        Thread.sleep(1000);
        t1.interrupt();

// 过时了        Thread.currentThread().stop();

    }


}

