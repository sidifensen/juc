import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test4 {
    public static void main(String[] args) {
        Thread t = new Thread("t1"){
            @Override
            public void run() {
                log.info("Thread t1 is running");
            }
        };
        log.info("Creating thread t1"+t.getState());
        t.start();//就绪
        log.info("Main thread is running");
        log.info("Creating thread t1"+t.getState());
    }
}
