import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.hash;

public class Test18 {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        //computeIfAbsent()方法，如果key不存在，则使用提供的函数计算值并添加到map中
        Integer i = map.computeIfAbsent("key", k -> 1);
        System.out.println(i);

        System.out.println(hash(1));

    }
}
