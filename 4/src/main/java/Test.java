import lombok.extern.slf4j.Slf4j;

public class Test {

    public static void main(String[] args) {
        Number n1 = new Number();
        new Thread(() -> {
            n1.a();
        }).start();

        new Thread(() -> {
            n1.b();
        }).start();
    }

}

@Slf4j
class Number {
    public static synchronized void a() { //锁的是类对象
        log.debug("1" );
    }

    public synchronized void b() {
        log.debug("2");
    }
}
