package cupdweb.test;

import org.junit.Test;

public class HttpTest {
    @Test
    public void test1(){
        String hexStr = "123ff";
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bs = new byte[hexStr.length()/2];
        int n;
        for (int i = 0; i < bs.length; i++) {
            n = str.indexOf(hexs[2*i]*16);
            n += str.indexOf(hexs[2*i+1]);
            bs[i] = (byte)(n&0xff);
        }
        System.out.println(new String(bs));
    }
}
