package cupdweb.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class Tool {
    public static String localAddress = getLocalAddress();

    /**
     * 本机ip
     * @return
     */
    public  static String getLocalAddress() {
        String address = null;
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * 获取参数
     */
    public static String getParam(String param){
        String value = "";
        if (StringUtils.isEmpty(param)){
            return  null;
        }
        if ("stmtPath".equalsIgnoreCase(param)){
            value = SystemConf.getStmtPath();
        }
        if ("smsIp".equalsIgnoreCase(param)){
            value = SystemConf.getSmsIp();
        }
        if("smsPort".equalsIgnoreCase(param)){
            value = SystemConf.getSmsPort();
        }
        if ("bgIp".equalsIgnoreCase(param)){
            value = SystemConf.getBgIp();
        }
        return value;
    }

    /**
     * yyyyMMddHHmmss
     * @return
     */
    public static String getYMDHms(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())); 也可以
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getYMDHms1(){
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 获取前一天  yyyyMMdd
     */
    public static String getLastDate(String date){
        try {
            Date temp = new SimpleDateFormat("yyyyMMdd").parse(date);
            temp.setTime(temp.getTime()-24*60*60*1000);
            return new SimpleDateFormat("yyyyMMdd").format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
            return  date;
        }
    }

    /**
     * 获取后一天 yyyyMMdd
     */
    public static String getNextDate(String date){
        try {
            Date temp = new SimpleDateFormat("yyyyMMdd").parse(date);
            temp.setTime(temp.getTime()+24*60*60*1000);
            return  new SimpleDateFormat("yyyyMMdd").format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
            return  date;
        }
    }

    /**
     * 上月  0902
     */
    public static String getPrevMonth(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1; //0-11
        if (month==1) {
            year --;
            month=12;
        }else {
            month--;
        }
        String ym = "";
        if (month>9) {
            ym = ""+year+month;
        }else {
            ym = ""+year+"0"+month;
        }
        return  ym;
    }

    /**
     * 上月日期 0228
     */
    public static String getPrevDate(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int date = calendar.get(Calendar.DATE);
        return ""+(month>9?month:"0"+month)+date;
    }


    /**
     * 获取 传入日期的第n天  yyyyMMdd
     */
    public static String getDateN(String date, int n){
        try {
            Date temp = new SimpleDateFormat("yyyyMMdd").parse(date);
            temp.setTime(temp.getTime()+n*24*60*60*1000);
            return  new SimpleDateFormat("yyyyMMdd").format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
            return  date;
        }
    }

    /**
     * MD5加密
     */
    public static String MD5(String v){
        char[] hexDigits = {'1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        byte[] temp = v.getBytes();
        //TODO

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(temp);
            byte[] digest = md5.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *字符串填充或截取
     * @param v
     * @param flag 0左 1右
     * @param len 长度
     * @param fill
     * @return
     */
    public static String fillString(String v,int flag,int len,String fill){
        if (StringUtils.isEmpty(v)){
            v="";
        }
        if (v.length()>len){//截取
            if (0==flag){
                v = v.substring(v.length()-len);
            }else {
                v = v.substring(0,len);
            }
        }else { //填充
            for (int i=v.length();i<len;i++){
                if (flag==0){
                    v = fill + v;
                }else {
                    v = v+ fill;
                }
            }
        }
        return  v;
    }

    /**
     * 根据psize 生成list数组
     * @param all
     * @param psize 页面数据条数
     * @return
     */
    public static List[] getPrintList(List all, int psize){
        Assert.assertNotNull(all);
        int pcount = 0; // 总页数
        int size = all.size();//总数据

        //计算一共多少页
        if (size <= psize){
            pcount = 1;
        }else {
            if (size%psize == 0){
                pcount = size/psize;
            }else {
                pcount = size/psize +1;
            }
        }
        List [] result = new ArrayList [pcount]; // pcount 个arrayList
        for (int i = 0; i < pcount; i++) { // 每页读数据
            result [i] = new ArrayList();
            int a = pcount*i;
            int b = pcount*(i+1);
            if (i==pcount-1){
                b = size; // 结尾
            }
            for (int j = a;j<b;j++){
                result[i].add(all.get(j));
            }
        }
        return  result;
    }

    /**
     * 16进制 去除空格
     */
    public static String trimHex(String hex){
        if (hex != null && hex.length() > 0) {
            while (hex.startsWith("20")){ // 空格 16进制的20  10进制的32
                hex.substring(2);
            }
            while (hex.endsWith("20")){
                hex.substring(0,hex.length()-2);
            }
        }
        return  hex;
    }

    /**
     * 生成随机密码
     */
    public static String getRandomString(int len){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 格式化金额 ##0.00；
     */
    public static String toAmt(String amt){
        if (StringUtils.isEmpty(amt)){
            amt = "0";
        }
        double temp = Double.parseDouble(amt);
        DecimalFormat format = new DecimalFormat("#0.00");
        return  format.format(temp);
    }

    /**
     * 判断是否数字
     */
    public static boolean isNum(String str) {
        if (StringUtils.isEmpty(str)) {
            return  false;
        }
        Pattern p = Pattern.compile("[0-9]*");
        return p.matcher(str).matches();
    }

    /**
     * 格式化卡号
     * 6221 **** **** 1234   16位
     * 6221 **** ****1234   15位
     * 6221123 **** **** 1234  19位
     *
     */
    public static String fmtCard(String cardNum){
        if (StringUtils.isEmpty(cardNum)) {
            return cardNum;
        }
        if (cardNum.length() == 16){
            return cardNum.substring(0,4)+" **** **** "+ cardNum.substring(12);
        }
        if (cardNum.length() ==15){
            return cardNum.substring(0,4)+" *** ****" + cardNum.substring(11);
        }
        if (cardNum.length()==19) {
            return  cardNum.substring(0,7)+"**** ****" + cardNum.substring(15);
        }
        return  cardNum;
    }

    /**
     * 格式化手机号  177 **** 5568
     */
    public static String fmtMobile(String mobile) {
        mobile = mobile.trim();
        return mobile.substring(0,3)+" **** "+mobile.substring(mobile.length()-4);
    }


    /**
     * map 生成  a=1&b=2
     */
    public static String mapToGet(Map<String,String> map){
        StringBuffer sb  = new StringBuffer();
        List keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            sb.append((i==0?"":"&")+keys.get(i)+"="+map.get(keys.get(i)));
        }
        return sb.toString();
    }

    /**
     * 金额大写
     */
    public static String digitUpperCase(String num){
        String [] digits = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        String [] unit1 = {"","拾","佰","仟"};
        String [] unit2 = {"元","万","亿","万亿"};

        BigDecimal bigDecimal = new BigDecimal(num);
        String value = String.valueOf(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println(value);

        String head = value.substring(0, value.length()-2); // 整数
        String end = value.substring(value.length()-2); // 小数
        String headMoney = "";
        String endMoney="";

        if ("00".equals(end)) {// 小数处理完毕
            endMoney="整";
        }else {
            if (!"0".equals(end.substring(0,1))){
                endMoney += digits[Integer.valueOf(end.substring(0,1))] +"角";
            }else if ("0".equals(end.substring(0,1))&&!"0".equals(end.substring(1,2))){
                endMoney += "零"+digits[Integer.valueOf(end.substring(1,2))]+"分";
            }
        }
        //处理整数
        char[] chars = head.toCharArray();
        Map<String,Boolean> map = new HashMap<>();
        boolean zeroFlag = false; //0 连续出现标志
        int vidxtemp = 0;
        for (int i = 0; i < chars.length; i++) {
            int idx = (chars.length-1-i)%4;
            int vidx = (chars.length-1-i)/4;
            String s = digits[Integer.valueOf(chars[i])];// 不同

        }





        return  null;
    }

    /**
     *
     * @param start
     * @param end
     * @param format yyyyMMdd
     * @return 相差天数 end-start
     */
    public static int daysBetween(String start, String end, String format) {
        SimpleDateFormat format2 = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format2.parse(start));
            long time1 = calendar.getTimeInMillis();
            calendar.setTime(format2.parse(end));
            long time2 = calendar.getTimeInMillis();
            long days = (time2-time1)/(24*3600*1000);
            return Integer.valueOf(String.valueOf(days));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 对象转 json 转 Map
     * @param object
     * @return
     */
    public static Map<String,String> jsonToMap(Object object) {
        String json = JSON.toJSONString(object);
        JSONObject jsonObject = JSONObject.parseObject(json);
        System.out.println(jsonObject);
        Map<String,String> map = new HashMap<>();
        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            map.put(key, (String) jsonObject.get(key));
        }
        return  map;
    }





}
