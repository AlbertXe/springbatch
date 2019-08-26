package cupdweb.message.gzb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("body")
public class QueryPlatformReqBodyXmlBeanTQ100001 {
    String idType;
    String idNo;
    String ab_name;
    String mo_phone;

    public QueryPlatformReqBodyXmlBeanTQ100001() {
        this.idType = "18位身份证";
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getAb_name() {
        return ab_name;
    }

    public void setAb_name(String ab_name) {
        this.ab_name = ab_name;
    }

    public String getMo_phone() {
        return mo_phone;
    }

    public void setMo_phone(String mo_phone) {
        this.mo_phone = mo_phone;
    }
}
