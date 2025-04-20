import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test8 {
    public static void main(String[] args) {

        // 当前时间
        LocalDateTime now = LocalDateTime.now();

        //获取周四时间
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);

        if(now.compareTo(time) > 0){
            time = time.plusWeeks(1);
        }
//        System.out.println(time);

        Duration between = Duration.ofDays(Duration.between(now, time).toMillis());
//        System.out.println(between.toHours());

        long initialDelay = between.toMillis();


        //initialDelay 代表时间和周四时间差值
        //period 代表一周的间隔时间
        long period = 1000 * 60 * 60 * 24 * 7;// 一周
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() -> {

        },initialDelay,period, TimeUnit.SECONDS);
    }
}
