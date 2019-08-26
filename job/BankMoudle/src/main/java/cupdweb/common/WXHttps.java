package cupdweb.common;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

public class WXHttps {
    private static final Logger logger = LoggerFactory.getLogger(WXHttps.class);
    private static  String wxURL = "https://api.weixin.qq.com";
    private static  String zxURL = "http://131.87.5.16:8080";
    private static  String zxFlag = "0";

    static {
        InputStream inputStream = WXHttps.class.getResourceAsStream("/weixin.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = properties.getProperty(wxURL);
        String url2 = properties.getProperty(zxURL);
        String flag = properties.getProperty(zxFlag);
        if (url != null) {
            wxURL =url;
        }
        if (url2 != null) {
            zxURL = url2;
        }
        if (flag != null) {
            zxFlag = flag;
        }
    }

    /**
     * 获取 tocken
     * @param bankNum
     * @param appid
     * @param secret
     * @return
     */
    public static JSONObject getAccessToken(String bankNum, String appid, String secret){
        // 全局配置文件控制
        // 0 专线  1 公网
        if ("1".equals(zxFlag)){
            return  getAccessTockenGW(appid,secret);
        }
        return  null;
    }

    private static JSONObject getAccessTockenGW(String appid, String secret) {
        String url = wxURL+"cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
        logger.info("url is {}",url);
        return a(url,"GET",null);
    }

    private static JSONObject a(String url, String get, JSONObject json) {
        InputStream is = null;
        OutputStream os = null;
        HttpsURLConnection conn = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");//安全传输层协议
            sc.init(null,new TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
            //TODO
            URL realUrl =new URL(url);

            conn = (HttpsURLConnection) realUrl.openConnection();
            conn.setRequestMethod(get);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // 证书相关 这是区别 http的地方
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier((a,b) -> true);

            if ("POST".equals(get.toUpperCase())){
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            }
            conn.connect();
            if ("POST".equals(get.toUpperCase())){
                os = conn.getOutputStream();
                os.write(json.toString().getBytes("utf-8"));
            }
            is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            while (reader.ready()){
                sb.append(reader.read());
            }
            JSONObject obj = JSONObject.parseObject(sb.toString());

//            conn.set
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


}

class TrustAnyHostNameVertifier implements  HostnameVerifier{
    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
