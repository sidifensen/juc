import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

public class Test2 {
    public static void main(String[] args) {

        MessageQueue messageQueue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                messageQueue.put(new Message(id, "消息"+id));
            },"生产者"+i).start();
        }

        new Thread(() -> {
            try {
                while (true){
                    Thread.sleep(1000l);
                    messageQueue.take();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"消费者").start();
    }

}

//消息队列类
@Slf4j
class MessageQueue {

    private LinkedList<Message> list = new LinkedList<>();

    //队列容量
    private int capacity;

    public MessageQueue(int capacity){
        this.capacity = capacity;
    }

    public Message take(){
        synchronized(list){
            while(list.isEmpty()){
                try {
                    log.info("队列为空，等待消息");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //从队列头部取出消息
            Message message = list.removeFirst();
            log.info("取出消息："+message);
            list.notifyAll();
            return message;
        }
    }

    public void put(Message message){
        synchronized (list){
            while(list.size() == capacity){
                try {
                    log.info("队列已满，等待消息");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //向队列尾部添加消息
            list.addLast(message);
            log.info("添加消息："+message);
            list.notifyAll();
        }
    }

}

//消息
class Message {
    private int id;

    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
