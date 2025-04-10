import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test3 {
    public static void main(String[] args) {
        new Thread(()->{
            while (true) {
                log.info("Hello from thread1");
            }
        }).start();
        new Thread(()->{
            while (true) {
                log.info("Hello from thread2");
            }
        }).start();
    }
}
