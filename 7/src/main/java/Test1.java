import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Slf4j
public class Test1 {
    public static void main(String[] args) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                synchronized (sdf){
//                    try {
//                        log.debug("{}", sdf.parse("1951-04-21"));
//                    } catch (Exception e) {
//                        log.error("{}", e);
//                    }
//                }
//            }).start();
//        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    TemporalAccessor parse = formatter.parse("1951-04-21");
                    log.debug("{}", parse);
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }).start();
        }
    }
}
