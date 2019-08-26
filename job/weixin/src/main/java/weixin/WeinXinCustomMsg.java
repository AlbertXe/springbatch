package weixin;


import com.alibaba.fastjson.JSONObject;
import weixin.pojo.ReqMsg;

import java.util.Map;

public class WeinXinCustomMsg {
    /**
     * 创建文本消息
     * @param msg
     * @param wxMenuText
     * @return
     */
    public static JSONObject createTextMsg(ReqMsg msg, WxMenuText wxMenuText){
        String menuText = wxMenuText.getMenuText();
        if (menuText!=null && menuText.contains("#bankNum")) {
            menuText.replaceAll("#bankNum", wxMenuText.getBankNum());
        }
        if (menuText.contains("#userId")) {
            menuText.replaceAll("#userId", msg.getFromUserName());
        }

        JSONObject rtn = new JSONObject();
        rtn.put("touser", msg.getFromUserName());
        rtn.put("msgtype", "text");
        rtn.put("text", new JSONObject().put("content", menuText));
        return  rtn;
    }


    /**
     * 生成微信 图文信息
     * @param reqMap
     * @param news
     * @return
     */
    public static JSONObject createNewsMsg(Map reqMap, TbMenuNews news){
        JSONObject article = new JSONObject();



        return  null;
    }
}
