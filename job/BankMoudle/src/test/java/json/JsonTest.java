package json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class JsonTest {
    @Test
    public void test01(){
        JSONObject json = new JSONObject();
        //language=JSON
        String a = "{\n" +
                "  \"name\": \"xie\",\n" +
                "  \"age\": \"28\"\n" +
                "}";
        JSONObject parse = (JSONObject) JSONObject.parse(a);
        System.out.println(parse.get("name"));
        JSONArray jsonArray = JSONObject.parseArray(a);  //" [{},{}]"
        for (int i = 0; i < jsonArray.size(); i++) {
            Object o = jsonArray.get(i);
            System.out.println(o);
        }

    }
}
