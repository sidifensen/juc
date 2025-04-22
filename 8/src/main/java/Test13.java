import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Test13 {
    private static final Logger log = LoggerFactory.getLogger(Test13.class);

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);//3是许可证的数量

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();//获取许可证
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("running..");
                try {
                    sleep(1000);
                    log.debug("end");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    semaphore.release();//释放许可证
                }
            }).start();
            
        }
    }
}
