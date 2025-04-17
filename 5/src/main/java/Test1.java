import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

@Slf4j
public class Test1 {
    // volatile 关键字可以保证变量的可见性和原子性， 易变
    static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (run) {
                // ....
                System.out.println("run = " + run);
            }
        });
        t.start();

        sleep(1);
        log.debug("停止 t");
        run = false; // 线程 t 不会如预想的停下来
    }
}
