package cupdweb.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 针对中断 进行监控
 */
public class BreakMonitorHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(BreakMonitorHandler.class);
    public static long now = System.currentTimeMillis();
    public static volatile boolean flag = true;
    public static void update(){
        flag = true;
    }
    @Override
    public void run() {
        while (true){
            if (flag){
                now = System.currentTimeMillis();
                flag = false;
            }else{
                long time = System.currentTimeMillis();
                // 中断超过5秒
                if (time - now >= 5000){
                    BankCache.submit(getErrorContent());
                }
            }

        }
    }

    private String getErrorContent() {
        StringBuffer sb = new StringBuffer();
        sb.append("日志中断告警\n");
        sb.append("告警时间：");
        return sb.toString();
    }
}
