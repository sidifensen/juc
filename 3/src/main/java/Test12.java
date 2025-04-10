import lombok.extern.slf4j.Slf4j;

public class Test12 {
    public static void main(String[] args) throws InterruptedException {
         TP tp = new TP();
         tp.start();

         Thread.sleep(3000);
         tp.stop();
    }


}

@Slf4j
class TP {
    private Thread monitor;

    public void start(){
        monitor = new Thread(()->{
            while (true){
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted){
                    log.info("被打断了");
                    break;
                }

                try {
                    Thread.sleep(1000);//1 睡眠打断,会抛出InterruptedException,重置为false
                    log.info("监控中...");//2 正常打断,true
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    //重新设置中断状态 设置为true
                    Thread.currentThread().interrupt();
                }
            }
        });
        monitor.start();

    }
    public void stop(){
        monitor.interrupt();
    }
}
