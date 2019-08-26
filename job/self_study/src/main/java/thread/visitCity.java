package thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class visitCity {
    @Test
    public void test1(){
        ExecutorService service = Executors.newFixedThreadPool(20);
        CountDownLatch in = new CountDownLatch(20);
        CountDownLatch out = new CountDownLatch(20);
        CountDownLatch wait = new CountDownLatch(1);

        for (int i = 0; i < 20; i++) {
            final int j=i+1;
            Runnable runnable = ()->{
                System.out.println("学生"+j+"上车");
                in.countDown();
                try {
                    wait.await();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("学生"+j+"下车");
                    out.countDown();
                }
            };
            service.submit(runnable);
        }
        try {
            in.await();
            System.out.println("都上车");
            wait.countDown();
            out.await();
            System.out.println("都下车");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
