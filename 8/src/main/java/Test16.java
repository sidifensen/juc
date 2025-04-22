import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Test16 {
    private static final Logger log = LoggerFactory.getLogger(Test16.class);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CountDownLatch countDownLatch = new CountDownLatch(10);

        Random random = new Random();

        String[] all = new String[10];
        for (int j = 0; j < 10; j++) {
            int k = j;
            executorService.submit(()->{
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    all[k] = i + "%"; //lambda表达式不能引用局部变量,只能引用局部常量
                    System.out.print("\r"+Arrays.toString(all));//"\r" 回车符，清除之前的输出，输出当前数组内容
                }
                countDownLatch.countDown();//计数减一
            });
        }
        countDownLatch.await();//等待计数器归零
        System.out.println("\ndone");
        executorService.shutdown();


    }
}
