import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Test9 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);


        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        System.out.println(unsafe);

        //1.获取域的偏移地址
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher teacher = new Teacher();

        //2.CAS操作
        unsafe.compareAndSwapInt(teacher,idOffset,0,1);
        unsafe.compareAndSwapObject(teacher,nameOffset,null, "张三");

        System.out.println(teacher.id);
        System.out.println(teacher.name);

    }
}


class Teacher{
    volatile int id;
    volatile String name;



}