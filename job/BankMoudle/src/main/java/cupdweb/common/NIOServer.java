package cupdweb.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.*;

public class NIOServer implements Runnable{
    private Selector selector;
    private int port;

    private static ExecutorService channel; // 接受socket线程池
    private static ExecutorService receive;// 处理队列任务
    private static ExecutorService send; // 发送队列任务

    private static ReadJedisQueue jedisQueue;

    public NIOServer(int port) {
        this.port = port;
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            //通道管理器
            selector =  Selector.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            //只有当该事件到达，才返回true 否则阻塞
            //将通道与通道管理器绑定，并注册accept事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        recordThread();
    }

    /**
     * 开启计数
     */
    private void recordThread() {

        new Thread(new BreakMonitorHandler()).start();
    }

    /**
     * 初始化队列和线程池
     */
    public void init(){
        //1  读写队列
        //2  线程池   核心 最大 存活时间 单位 队列 拒绝规则
        channel = new ThreadPoolExecutor(40, 60, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000),new RejectedExecutionHandler(){
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("exceed max deal in executorChannel");//超过 执行器最大数
            }
        });

        receive = new ThreadPoolExecutor(20, 50, 500, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(100),(r,executor)->{System.out.println("exceed max deal in executorReceive");});

        send = new ThreadPoolExecutor(20, 50, 500, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(100),new RejectedExecutionHandler(){
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("exceed max deal in executorSend");
            }
        });
    }


}
