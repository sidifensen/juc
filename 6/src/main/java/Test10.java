import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Test10 {
    public static void main(String[] args) {
        Account.demo(new MyAtomicInteger(10000));
    }

}

class MyAtomicInteger implements Account{
    static final Unsafe UNSAFE;

    private volatile int value;

    private static final long valueOffset;

    static {
        try {
            //获取Unsafe实例
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        //获取表示该字段的内存地址偏移量。
        try {
            valueOffset =  UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    public MyAtomicInteger(int i) {
        value = i;
    }

    public static Unsafe getUnsafe() {
        return UNSAFE;
    }


    public int getValue(){
        return value;
    }


    public void decrement(int amount){
        while(true){
            int prev = value;
            int next = prev - amount;
            if(UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)){
                return;
            }
        }

    }


    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }
}
