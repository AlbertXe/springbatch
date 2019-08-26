package cupdweb.common;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    /**
     * 金额保留两位小数
     * @param amt
     * @return
     */
    @Test
    public void amtToString(){
        String amt = "1234";
        DecimalFormat format = new DecimalFormat("##0.00");
        double v = Double.parseDouble(amt) / 100;
        String result = format.format(v);
        System.out.println(result);
//        return  result;
    }

    /**
     * 上月日期
     */
    public void getPrevDate(){
        Calendar c = Calendar.getInstance();
    }

    /**
     * 时间 150624
     */
    public void time(String start){
        Date date = new Date();
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

    }

}
