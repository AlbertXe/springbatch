package cupdweb.test;

import cupdweb.common.Tool;
import cupdweb.pojo.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ToolTest {
    Tool tool =null;
    @Before
    public void test(){
        tool = new Tool();
    }
    @Test
    public void test1(){
        String y = Tool.getYMDHms1();
        System.out.println(y);
    }
    @Test
    public void test2(){
        String dateN = Tool.getDateN("20190304", -4);
        System.out.println(dateN);
    }
    @Test
    public void test3(){
        String s = Tool.getRandomString(10);
        System.out.println(s);
    }

    @Test
    public void test4(){
        System.out.println(Tool.toAmt("350"));
    }
    @Test
    public void test5(){
        System.out.println(Tool.isNum("1234y"));
    }

    @Test
    public void test6(){
        Map map = new HashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        System.out.println(Tool.mapToGet(map));
    }
    @Test
    public void test7(){
        System.out.println(Tool.daysBetween("20180405", "20190401", "yyyyMMdd"));
    }

    @Test
    public void test8(){
        Person person = new Person("xie", "20");
        Map map = Tool.jsonToMap(person);
        System.out.println(map);
    }

    @Test
    public void test9(){
        Tool.digitUpperCase("100.335");
    }

    @Test
    public void test10(){
        System.out.println(Tool.getPrevMonth());
    }

    @Test
    public void test11(){
        System.out.println(Tool.getPrevDate());
    }

}
