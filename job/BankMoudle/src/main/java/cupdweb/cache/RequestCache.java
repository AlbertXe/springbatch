package cupdweb.cache;

import cupdweb.interceptor.WeiXinSafeInteceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestCache {
    // key:bankNum  value :时间
    private static final ConcurrentHashMap<String,Long> requestTime = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, AtomicInteger> requestCount = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> requestDetail = new ConcurrentHashMap<>();


    public static boolean updateCacheInf(HttpServletRequest request){
        String ip = getIp(request);
        String bankNum = request.getParameter("bankNum");
        if (bankNum == null) {
            bankNum ="0000";
        }
        return  updateCacheInf(bankNum,ip);
    }

    /**
     * 记录请求ip  返回false表示 黑名单
     * @param bankNum
     * @param ip
     * @return
     */
    private static boolean updateCacheInf(String bankNum, String ip) {
        //1. 参数开关 直接跳过这个黑名单
        //2. 缓存中有记录 直接黑名单
        //3. 判断请求次数
        int count = getRequestCount(bankNum); // 银行 请求总量
        setRequsetDetail(bankNum,ip); // 银行 ip 次数
        
        if (count>=100){ // 当队列中数目超过 设置的预计值， 判断请求并清空
            boolean flag = checkRequest(requestDetail.get(bankNum),bankNum,requestTime.get(bankNum));
            return flag;
        }

        return  false;
    }

    /**
     * 检测有无暴力请求
     * 同一 ip 密集 请求时 false, 加入黑名单
     * @param requestMap
     * @param bankNum
     * @param start
     * @return
     */
    private static boolean checkRequest(ConcurrentHashMap<String, Integer> requestMap, String bankNum, Long start) {
        long now = System.currentTimeMillis();
        for (String ip:requestMap.keySet()){
            boolean flag = true;
            Integer count = requestMap.get(ip);
            if (count >100){
                flag =false;
            }
            if (count>10 && (now-start)<2000){
                flag = false;
            }
            if (!flag){ //问题请求需要处理
                dealReq(bankNum,ip,count,start);
                return false;
            }
        }
        return  true;
    }

    private static void dealReq(String bankNum, String ip, Integer count, Long start) {
        //1. 加入黑名单
            //1.1 加入缓存  PropCache
            //1.2 加入数据库 tb_prop_inf
        //2. 发送警告
        String msg = getWarnMsg(bankNum,ip,count,start);
        WeiXinSafeInteceptor.submitContent(msg);
    }

    private static String  getWarnMsg(String bankNum, String ip, Integer count, Long start) {
        StringBuffer sb = new StringBuffer();
        sb.append("银行："+bankNum+"请求告警监控\n");
        sb.append("告警时间："+System.currentTimeMillis());
        sb.append("告警ip:"+ip);
        sb.append("100次请求中次数："+count);
        sb.append("请求初始时间："+start);
        return  sb.toString();
    }

    /**
     * 保存 ip 到队列
     * @param bankNum
     * @param ip
     */
    private static void setRequsetDetail(String bankNum, String ip) {
        if (!requestDetail.contains(bankNum)){
            requestDetail.put(bankNum, new ConcurrentHashMap<String,Integer>());
        }
        ConcurrentHashMap<String, Integer> detail = requestDetail.get(bankNum);
        if (detail.contains(ip)){
            detail.put(ip, detail.get(ip)+1);
        }else {
            detail.put(ip, 1);
        }


    }

    /**
     * 请求总量 ++
     * @param bankNum
     * @return
     */
    private static int getRequestCount(String bankNum) {
        if(!requestTime.contains(bankNum)){
            requestTime.put(bankNum, System.currentTimeMillis());
        }
        if (!requestCount.contains(bankNum)){
            requestCount.put(bankNum, new AtomicInteger(0));
        }
        AtomicInteger count = requestCount.get(bankNum);
        return  count.incrementAndGet();
    }

    private static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forward-for"); // 代理一般会有此请求头
        if (ip == null || ip.length()==0 || ip.equals("unknown")){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length()==0 || ip.equals("unknown")){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length()==0 || ip.equals("unknown")){
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")){// 多个IP 取第一个
           ip =  ip.split(",")[0];
        }
        return ip;
    }
}
