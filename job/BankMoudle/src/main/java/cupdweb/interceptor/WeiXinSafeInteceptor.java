package cupdweb.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * weixin 安全拦截器
 */
public class WeiXinSafeInteceptor extends HandlerInterceptorAdapter {
    //最后一个参数是 对拒绝任务的处理策略：一共四中策略
    //1.CallerRunsPolicy  重试添加当前任务 重复执行execute 知道成功
    //2 AbortingPolicy 丢弃 报异常
    //3 DiscardPolicy 无声抛弃
    //4 DiscardOldestPolicy 抛弃队列最久的，将当前加入队列
    // 处理任务的优先级 1.核心线程数 2.任务队列 3. 最大线程数 4.处理策略
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 100, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(100),new ThreadPoolExecutor.DiscardOldestPolicy());



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> parameterNames = request.getParameterNames();
        String bankNum = request.getParameter("bankNum");
        String userId = request.getParameter("userId");
        if (userId == null || bankNum == null   ) {
            throw  new Exception();
        }
        while (parameterNames.hasMoreElements()){
            String element = parameterNames.nextElement();

        }


        return super.preHandle(request, response, handler);
    }

    /**
     * 对公的一个任务 推送相关消息
     * @param msg
     */
    public static void submitContent(String msg) {
        executor.submit(new MsgSendHandler(msg));
    }
}
