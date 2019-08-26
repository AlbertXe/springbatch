package cupdweb.common;

import java.io.*;
import java.util.Properties;

public class SystemConf {
    static Properties properties;

    private static String stmtPath ;
    private static String smsIp;
    private static String smsPort;
    private static String bgIp;

    static {
        loadSysParam();
        stmtPath = properties.getProperty("stmtPath");
        smsIp = properties.getProperty("smsIp");
        smsPort = properties.getProperty("smsPort");
        bgIp = properties.getProperty("bgIp");
    }

    private static void loadSysParam() {
        properties = new Properties();
        File file = new File("/app/weixin/conf/wxpush/a.txt");
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            properties.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStmtPath() {
        return stmtPath;
    }

    public static void setStmtPath(String stmtPath) {
        SystemConf.stmtPath = stmtPath;
    }

    public static String getSmsIp() {
        return smsIp;
    }

    public static void setSmsIp(String smsIp) {
        SystemConf.smsIp = smsIp;
    }

    public static String getSmsPort() {
        return smsPort;
    }

    public static void setSmsPort(String smsPort) {
        SystemConf.smsPort = smsPort;
    }

    public static String getBgIp() {
        return bgIp;
    }

    public static void setBgIp(String bgIp) {
        SystemConf.bgIp = bgIp;
    }
}
