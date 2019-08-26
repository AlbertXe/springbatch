package cupdweb.common;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;

public class XmlUtil {

    public static String toXml(Object obj ,String charset){
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"");
        sb.append(charset);
        sb.append("\"?>");
        sb.append(toXmlNoTitle(obj));
        return  sb.toString();
    }

    private static String toXmlNoTitle(Object obj) {
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()){
            public HierarchicalStreamWriter createWriter(Writer out){
                return  new PrettyPrintWriter(out){
                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }

                    @Override
                    public String encodeNode(String name) {
                        return name;
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (text.startsWith("<![DATA[") && text.endsWith("]]>")){
                            writer.write(text);
                        }else {
                            super.writeText(writer, text);
                        }
                    }
                };
            }
        });
        xStream.processAnnotations(obj.getClass());
        String xml = xStream.toXML(obj);
        return  xml;
    }

}
