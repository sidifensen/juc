import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class Test2 {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);

        System.out.println(updateAndGet(i, x -> x + 1));
    }

    public static int updateAndGet(AtomicInteger i, IntUnaryOperator operator){
        while (true){
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if(i.compareAndSet(prev,next)){
                return next;
            }
        }
    }
}
