package com.example.springbatch.demo16;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


/**
 * HASEE
 * 2019/7/27 9:51
 */
@Component
public class RetryProcessor implements ItemProcessor<String,String> {
    private int count =0 ;

    @Override
    public String process(String item) throws Exception {
        System.out.println(item);
        if (item.equals("26")) {
            count ++;
            if (count > 3) {
                System.out.println(count + "sucess");
                return "-" + item;
            }else{
                System.out.println(count + "false");
                throw new RuntimeException("error");
            }
        }else{
            return "-"+item;
        }
    }
}
