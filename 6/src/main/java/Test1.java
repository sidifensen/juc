import java.util.concurrent.atomic.AtomicInteger;

public class Test1 {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);

        System.out.println(i.incrementAndGet());// ++i 新增并获取原子值
        System.out.println(i.getAndIncrement());// i++ 获取原子值并新增

        System.out.println(i.decrementAndGet());// --i 减少并获取原子值
        System.out.println(i.getAndDecrement());// i-- 获取原子值并减少

        System.out.println(i.addAndGet(10));// i+=10 原子性地增加10并获取原子值
        System.out.println(i.getAndAdd(10));// i=i+10 原子性地获取原子值并增加10

        System.out.println(i.compareAndSet(0, 100));// 如果i原子值为0，则将其设置为100并返回true，否则返回false

        i.updateAndGet(x -> x *2);
        i.getAndUpdate(x -> x *2);


        System.out.println(i.get());// 获取原子值
    }
}
