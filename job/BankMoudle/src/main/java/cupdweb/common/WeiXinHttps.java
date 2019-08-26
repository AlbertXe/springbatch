package cupdweb.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeiXinHttps {

    /**
     * http 方式发送请求
     */
    public static JSONObject httpSend(String url,String method,JSONObject req){
        InputStream in =null;
        OutputStream out = null;
        JSONObject jsonObject = null;

        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

            conn.setReadTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod(method);
            if ("POST".equals(method.toUpperCase())){
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            }

            conn.connect();
            if ("POST".equals(method.toUpperCase())){// 写出去 请求
                out = conn.getOutputStream();
                out.write(req.toString().getBytes("utf-8"));
                out.flush();
            }

            in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String s = null;
            StringBuffer sb = new StringBuffer();
            while ((s = reader.readLine())!= null){
               sb.append(s);
            }
//            while(reader.ready()){ //非阻塞
//                sb.append(reader.readLine());
//            }
            jsonObject = JSON.parseObject(sb.toString());
            if (!"0".equals(jsonObject.getString("errcode"))){ //  非0 就是错误
                try {
                    throw new Exception("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }
}
