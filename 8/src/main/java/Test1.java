import javafx.concurrent.Worker;
import jdk.nashorn.internal.objects.annotations.Function;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Test1 {
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool
                (1,1000,TimeUnit.MILLISECONDS,1,(queue,task)->{
                    //1.死等
//                    queue.put(task);
                    //2.带超时等待
//                    queue.offer(task,1500,TimeUnit.MILLISECONDS);
                    //3.让调用者线程放弃执行任务
//                    log.info("放弃执行任务"+task);
                    //4.抛出异常
//                    throw new RuntimeException("任务队列已满");
                    //5.让调用者自己执行任务
                    task.run();
                });

        for (int i = 0; i < 3; i++) {
            int id = i;
            pool.execute(()->{
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("执行任务"+id);
            });
        }
    }

}

//拒绝策略
@FunctionalInterface//函数式接口
interface RejectPolicy<T>{
    void reject(BlockingQueue<T> queue,T task);
}

@Slf4j
class ThreadPool {
    //任务队列
    private BlockingQueue<Runnable> taskQueue;

    //线程集合
    private HashSet<Worker> workers = new HashSet<>();

    // 核心线程数
    private int coreSize;

    //获取任务的超时时间
    private long timeout;

    private TimeUnit unit;

    private RejectPolicy<Runnable> rejectPolicy;

    //执行任务
    public void execute(Runnable task){
        //当任务数没有超过coreSize时,直接交给 worker 执行
        //当任务数超过coreSize时,将任务放入taskQueue
        synchronized(workers){
            if (workers.size() < coreSize){
                Worker worker = new Worker(task);
                log.info("创建线程执行任务"+worker+ "  task: "+ task);
                workers.add(worker);//添加到线程集合

                worker.start();
            }else {
//                taskQueue.put(task);
                //队列满了
                //1.一直等
                //2.带超时等待
                //3.让调用者线程放弃执行任务
                //4.抛出异常
                //5.让调用者自己执行任务
                taskQueue.tryPut(rejectPolicy,task);
            }
        }
    }

    //线程
    class Worker extends Thread {

        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            //执行任务
            //1.task不为空,执行任务
            //2.当task执行完毕,再接着从taskQueue任务队列中获取任务并执行
//            while(task != null || (task = taskQueue.take()) != null){
            while(task != null || (task = taskQueue.poll(timeout, unit)) != null){
                try{
                    log.info("worker执行任务..." + task);
                    task.run();//执行任务
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task = null;//任务执行完毕,置空
                }
            }
            synchronized(workers){
                log.info("线程结束..."+this);
                workers.remove(this);//从线程集合中移除
            }
        }
    }

    public ThreadPool(int coreSize, long timeout, TimeUnit unit, int queueCapacity,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.unit = unit;
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }
}

@Slf4j
class BlockingQueue<T> {

    //1.任务队列
    private Deque<T> queue = new ArrayDeque<>();

    //2.锁
    private ReentrantLock lock = new ReentrantLock();

    //3.生产者条件变量
    private Condition fullWaitSet = lock.newCondition();

    //4.消费者条件变量
    private Condition emptyWaitSet = lock.newCondition();

    //5.容量
    private int capacity;

    public BlockingQueue(int capacity){
        this.capacity = capacity;
    }

    //带超时的阻塞获取
    public T poll(long timeout, TimeUnit unit){
        lock.lock();
        try{
            //将超时时间转换为纳秒
            long nanos = unit.toNanos(timeout);
            while(queue.isEmpty()){
                try {
                    if(nanos <= 0){
                        return null;
                    }
                    //awaitNanos(long timeout)方法，超时时间为纳秒，返回值为剩余时间
                    nanos = emptyWaitSet.awaitNanos(nanos);//阻塞等待
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T t = queue.removeFirst();//从队首取出元素
            fullWaitSet.signal(); // 唤醒一个阻塞的生产者
            return t;
        }finally {
            lock.unlock();
        }
    }

    // 阻塞获取
    public T take(){
        lock.lock();
        try{
            while(queue.isEmpty()){
                try {
                    emptyWaitSet.await();//阻塞等待
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            T t = queue.removeFirst();//从队首取出元素
            fullWaitSet.signal(); //唤醒一个阻塞的生产者
            return t;
        }finally {
            lock.unlock();
        }
    }

    // 阻塞添加
    public void put(T t){
        lock.lock();
        try{
            while(queue.size() == capacity){
                try {
                    log.info("队列已满..."+ t);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.addLast(t);//添加到队尾
            log.info("任务放入任务队列" + t);
            emptyWaitSet.signal(); //唤醒一个阻塞的消费者
        }finally {
            lock.unlock();
        }
    }

    //带超时的阻塞添加
    public boolean offer(T task,long timeout,TimeUnit unit){
        lock.lock();
        try{
            long nanos = unit.toNanos(timeout);
            while(queue.size() == capacity){
                try {
                    log.info("队列已满..."+ task);
                    if (nanos <= 0){
                        return false;
                    }

                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.addLast(task);//添加到队尾
            log.info("任务放入任务队列" + task);
            emptyWaitSet.signal(); //唤醒一个阻塞的消费者

            return true;
        }finally {
            lock.unlock();
        }
    }

    // 获取队列大小
    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try{
            //判断队列是否已满
            if(queue.size() == capacity){
                rejectPolicy.reject(this,task);
            }else{
                //队列未满,尝试添加任务
                queue.addLast(task);
                log.info("任务放入任务队列" + task);
                emptyWaitSet.signal(); //唤醒一个阻塞的消费者
            }
        }finally {
            lock.unlock();
        }
    }


}
