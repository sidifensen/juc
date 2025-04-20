import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Test9 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);

        Integer invoke = pool.invoke(new MyTask(100));

        System.out.println(invoke);
    }
}

//计算1-n之间的和
@Slf4j
class MyTask extends RecursiveTask<Integer>{

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        //终止条件
        if(n == 1){
            return 1;
        }
        MyTask t1 = new MyTask(n - 1);//创建子任务
        t1.fork();//拆分任务,让一个线程执行任务

        Integer result = n + t1.join();//得到任务结果
        return result;
    }
}