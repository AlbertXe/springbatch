package cupdweb.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

    public static void sendPost(String url,String param){
        InputStream is = null;
        OutputStream os = null;

        try {
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POSt");
            connection.setUseCaches(false);
            //application/x-www-form-urlencoded 数据被编码为名称/值对。这是标准的编码格式  get方法 post方法 无type=file
            //multipart/form-data： 数据被编码为一条消息，页上的每个控件对应消息中的一个部分 post type=file控件
            //text/plain： 数据以纯文本形式(text/json/xml/html)进行编码 文本json
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencode;charset=UTF-8");

            connection.connect();
            os = connection.getOutputStream();
            os.write(param.getBytes("utf-8"));
            os.flush();

            is = connection.getInputStream();
            byte[] b = new byte[2048];
            int len=0;
            StringBuffer sb = new StringBuffer();
            while ((len= is.read(b))!= -1){
                String s = new String(b, "utf-8");
                sb.append(b);
            }
            connection.disconnect();
            String result = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String hexStr2Str(String hexStr){
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bs = new byte[hexStr.length()/2];
        int n;
        for (int i = 0; i < bs.length; i++) {
            n = str.indexOf(hexs[2*i]*16);
            n += str.indexOf(hexs[2*i+1]);
            bs[i] = (byte)(n&0xff);
        }
        return  new String(bs);
    }
}
