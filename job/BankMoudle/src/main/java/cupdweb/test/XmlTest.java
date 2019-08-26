package cupdweb.test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import cupdweb.pojo.Person;
import org.junit.Before;
import org.junit.Test;

import java.io.Writer;

public class XmlTest {

    private Person person;

    @Before
    public void init(){
        person = new Person("谢洪伟", "28");
    }

    @Test
    public  void test1(){
        XStream xStream = new XStream();
        String person = xStream.toXML(new Person("谢洪伟", "28"));
        System.out.println(person);
//        <cupdweb.pojo.Person>
//          <name>谢洪伟</name>
//          <age>28</age>
//        </cupdweb.pojo.Person>
    }

    @Test
    public void test2(){
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
        xStream.processAnnotations(person.getClass());
        String xml = xStream.toXML(person);
        System.out.println(xml);
    }
}
