import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test8 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                int count = 0;
                for(;;){
                    log.info("t1              count: " + count++);
                }
            }
        };

        Thread t2 = new Thread("t2"){
            @Override
            public void run() {
                int count = 0;
                for(;;){
//                    Thread.yield();
                    log.info("t2  count: " + count++);
                }
            }
        };

        t1.start();
        t1.setPriority(Thread.MIN_PRIORITY);

        t2.start();
        t2.setPriority(Thread.MAX_PRIORITY);


    }
}
