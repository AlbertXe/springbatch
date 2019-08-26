import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JVMTest {
    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 10000; i++) {
//            System.out.println("1");
//            Thread.sleep(1000);
//        }

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Hashtable hashtable = new Hashtable();
                for (int i = 0; i < 10000; i++) {
                    System.out.println(i);
                    hashtable.put(i, i);
                }
            }
        });

    }

}
