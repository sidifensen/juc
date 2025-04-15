import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

@Slf4j
public class Test1 {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0 ; i < 3 ; i++){
            new People().start();
        }

        Thread.sleep(1000l);
        Mailboxes.getIds().forEach(id -> new Postman(id,"hello world "+id).start());

//        GuardedObject guardedObject = new GuardedObject();
//        new Thread(()->{
//            log.debug("等待结果");
//            List<String> list = (List<String>)guardedObject.get(2000l);
//            log.debug("结果："+list);
//        },"t1").start();
//
//        new Thread(()->{
//            log.debug("下载");
//            try {
//                List<String> download = Downloader.download();
//                Thread.currentThread().sleep(1000);
//                guardedObject.complete(download);
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"t2").start();

    }

}

@Slf4j
class People extends Thread{

    @Override
    public void run() {
        //收信
        GuardedObject guardedObject = Mailboxes.createGuardedObject();
        log.debug("等待收件 "+guardedObject.getId());
        Object mail = guardedObject.get(5000l);//超时时间为5s
        log.debug("收到的邮件:"+mail);
    }
}

@Slf4j
class Postman extends Thread{

    private int id;

    private String mail;

    public Postman(int id,String mail){
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObject guardedObject = Mailboxes.getGuardedObject(id);
        guardedObject.complete(mail);
        log.debug("邮递的邮件:"+mail);
    }
}

class Mailboxes {

    private static Map<Integer,GuardedObject> boxes = new Hashtable<>();

    private static int id = 1;

    //产生唯一id
    public static synchronized int generateId(){
        return id++;
    }

    public static GuardedObject getGuardedObject(int id){
        return boxes.remove(id);//获得GuardedObject并从boxes中移除
    }

    public static GuardedObject createGuardedObject(){
        GuardedObject guardedObject = new GuardedObject(generateId());
        boxes.put(guardedObject.getId(),guardedObject);
        return guardedObject;
    }

    public static Set<Integer> getIds(){
        return boxes.keySet();
    }

}

class GuardedObject {

    private int id;

    public GuardedObject(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private Object response;

    public Object get(Long timeout){
        synchronized(this){
            //开始时间
            long begin = System.currentTimeMillis();
            //经历的时间
            long passTime = 0;

            while (response == null){

                long waitTime = timeout - passTime;

                //经历的时间超过了timeout最大等待时间 则退出循环
                if(waitTime<=0){
                    break;
                }

                try {
                    this.wait(waitTime);//防止虚假唤醒 等待timeout-passTime时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //经历时间
                passTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }


    public void complete(Object response){
        synchronized(this){
            this.response = response;
            this.notifyAll();
        }
    }
}
