import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

@Slf4j
public class Test2 {
    // volatile 关键字可以保证变量的可见性和原子性， 易变
    static boolean run = true;

    final static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (run) {
                synchronized(lock){
                    if(!run){
                        break;
                    }
                }
            }
        });
        t.start();

        sleep(1000);
        log.debug("停止 t");
        synchronized(lock){
            //在同步块中将run变量的值设置为false，以通知线程t停止运行
            run = false;
        }
    }
}
