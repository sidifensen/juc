import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Test1 {
    public static void main(String[] args) {

        Runnable runnable = () -> System.out.println("Hello World");
        Thread thread = new Thread(runnable, "MyThread");
        thread.start();


    }

}
