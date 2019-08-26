import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Buf {
    private List list = new ArrayList();

    public synchronized  void put(String s){
        if(list.size() == 5){  // if == while 就 ok
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(s);
        notifyAll();
    }

    public synchronized  void get(){
        if (list.size() ==0){  // if 边 while
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.remove(0);
        notifyAll();
    }

    synchronized int size(){
        return list.size();
    }

    public static void main(String[] args) {
        Buf b =new Buf();

        ExecutorService executorService = Executors.newFixedThreadPool(12);
        for (int i = 0; i < 2; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        System.out.println("before"+Thread.currentThread().getName());
                        b.put("1");
                        System.out.println("after:"+Thread.currentThread().getName());
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        System.out.println("before:"+Thread.currentThread().getName());
                        b.get();
                        System.out.println("after:"+Thread.currentThread().getName());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }


    }
}
