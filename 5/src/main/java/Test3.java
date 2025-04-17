import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test3 {

    public void main(String[] args) throws InterruptedException {
        TPTVolatile t = new TPTVolatile();
        t.start();
        Thread.sleep(3500);
        log.debug("stop");
        t.stop();
    }

    // 停止标记用 volatile 是为了保证该变量在多个线程之间的可见性
  // 我们的例子中，即主线程把它修改为 true 对 t1 线程可见
    class TPTVolatile {
        private Thread thread;
        private volatile boolean stop = false;
        public void start(){
            thread = new Thread(() -> {
                while(true) {
                    Thread current = Thread.currentThread();
                    if(stop) {
                        log.debug("料理后事");
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                        log.debug("将结果保存");
                    } catch (InterruptedException e) {
                    }
                    // 执行监控操作
                }
            },"监控线程");
            thread.start();
        }
        public void stop() {
            stop = true;
            //让线程立即停止而不是等待sleep结束
            thread.interrupt();
        }
    }
}
