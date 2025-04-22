import java.util.concurrent.*;

public class Test19 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<Boolean> task = ()->{
            System.out.println("Task running");
            return true;
        };
        Future<Boolean> submit = executorService.submit(task);
        System.out.println("Future result: " + submit.get());

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2,
                0l, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

    }
}
