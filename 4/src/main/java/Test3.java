import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;


@Slf4j
public class Test3 {
    static volatile int count = 10;
    static final Object Lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            // 期望减到 0 退出循环
            while (count > 0) {
                try {
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count--;
                log.debug("count: {}", count);
            }
        },  "t1").start();

        new Thread(() -> {
            // 期望超过 20 退出循环
            while (count < 20) {
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                log.debug("count: {}", count);
            }
        }, "t2").start();
    }

}
