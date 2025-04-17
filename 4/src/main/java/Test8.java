import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test8 {
    static final Object Lock = new Object();
    // 表示 t2 是否运行过
    static boolean t2runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (Lock) {
                while (!t2runned) {
                    try {
                        Lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            log.debug("1");
        },  "t1");

        Thread t2 = new Thread(() -> {
            synchronized (Lock) {
                log.debug("2");
                t2runned = true;
                Lock.notify();
            }
        },  "t2");

        t1.start();
        t2.start();
    }

}
