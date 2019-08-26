package cupdweb.common;

import redis.clients.jedis.JedisCluster;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class JedisUtil {
    public static final ReentrantLock lock = new ReentrantLock(false);
    public static final Condition condition = lock.newCondition();
    public static JedisCluster cluster = null;

    static String prefix = "wxpush_dev";
    static String SPLIT = ":";
    public String nameKey = prefix+SPLIT+"read ";
    public byte[] bytekey = nameKey.getBytes();

    public abstract void init();
}
