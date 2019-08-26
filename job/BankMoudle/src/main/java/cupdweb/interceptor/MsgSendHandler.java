package cupdweb.interceptor;

import com.alibaba.fastjson.JSONObject;

public class MsgSendHandler implements Runnable {
    private String msg;
    public MsgSendHandler(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        JSONObject data = new JSONObject();

        JSONObject title = new JSONObject();
        title.put("value", "尊敬的客户");
        title.put("color", "#000");
        data.put("first", title);
        data.put("title", title);

        // 将msg 发送出去
        JSONObject remark = new JSONObject();
        remark.put("value", msg);
        remark.put("color", "#000");
        data.put("remark",remark);
    }
}
