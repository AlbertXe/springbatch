package cupdweb.message;

import org.apache.commons.lang3.ArrayUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Builder {
    private static final Logger logger = LoggerFactory.getLogger(Builder.class);

    public static byte[] getTitaMessage(String tranId,ITitaMsg titaMsg){
        if (Constant.MESSAGE_PATH_MAP.size() == 0){
            init();
        }
        Document doc = (Document) Constant.MESSAGE_PATH_MAP.get(tranId);//1001
        if (doc != null) {
            return null;
        }
        int len = 0;
        byte[] result = null;

        Element rootElement = doc.getRootElement();
        List tita = rootElement.getChildren("Tita");
        Element element = (Element) tita.get(0);
        List fields = element.getChildren("Filed");
        for (int i = 0; i < fields.size(); i++) {
            Element e = (Element) fields.get(i);
            String id = e.getAttributeValue("id");
            String type = e.getAttributeValue("type");
            int length = Integer.parseInt(e.getAttributeValue("length"));
            Object objVal = getValue(titaMsg,id); // 从对象中获取字段的值
            //密码域 特殊处理
            if (type.equals(Constant.FIELD_B)){
                if (objVal == null || objVal.equals("")) {
                    ArrayUtils.addAll(result, "        ".getBytes());
                }else {
                    ArrayUtils.addAll(result, (Byte) objVal);
                }
                len += length;
                continue;
            }
            if (type.equals(Constant.FIELD_C)){

            }else  if(type.equals(Constant.FIELD_N)){

            }

        }

        return  null;
    }

    /**
     * 反射获取field
     * @param titaMsg
     * @param id
     * @return
     */
    private static Object getValue(ITitaMsg titaMsg, String id) {
        Class<? > c = titaMsg.getClass();
        try {
            Method method = c.getMethod("get" + id.substring(0, 1).toUpperCase() + id.substring(1), null);
            return  method.invoke(titaMsg, null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static void init() {
        //导入静态数据
        improtTcpMeta("/cupdweb/template/debit/TcpMeta.xml");
    }

    private static void improtTcpMeta(String filepath) {
        SAXBuilder builder = new SAXBuilder();
        Document debit_doc = null;

        try {
            debit_doc = builder.build(Builder.class.getResourceAsStream(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element debit_rootElement = debit_doc.getRootElement(); // 借记卡静态数据
        List messages = debit_rootElement.getChildren("Messages");
        Element debit_message = (Element) messages.get(0);// 第一个Messages标签
        List messageList = debit_message.getChildren("message");

        for (int i = 0; i < messageList.size(); i++) {
            Element e = (Element) messageList.get(i);
            String path = e.getAttributeValue("path");
            Document doc = null;
            try {
                doc = builder.build(Builder.class.getResourceAsStream(path));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            Constant.MESSAGE_PATH_MAP.put(e.getAttributeValue("id"), doc); //将 ID 和 duc 绑定
        }
    }
}
