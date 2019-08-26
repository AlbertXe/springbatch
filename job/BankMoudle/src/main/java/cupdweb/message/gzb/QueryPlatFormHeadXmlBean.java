package cupdweb.message.gzb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("head")
public class QueryPlatFormHeadXmlBean {

    public QueryPlatFormHeadXmlBean() {
        this.channel = "WX";
        this.page_size="20";
        this.page_num = "1";
    }

    /*
     * 报文参考号
     */
    String msg_ref;

    /*
     *渠道
     */
    private String channel;

    /*
     *交易代码
     */
    private String trans_code;

    /*
     *机构编号
     */
    private String trans_branch;

    /*
     *渠道交易日期
     */
    private String trans_date;
    /*
     *每条页数
     */
    private String page_size;
    /*
     *当前页数
     */
    private String page_num;
    /*
     *工号
     */
    private String trans_oper;


    /*
     *总页数
     */
    private String page_total;

    /*
     *备注
     */
    private String remark;

    public String getMsg_ref() {
        return msg_ref;
    }

    public void setMsg_ref(String msg_ref) {
        this.msg_ref = msg_ref;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTrans_code() {
        return trans_code;
    }

    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code;
    }

    public String getTrans_branch() {
        return trans_branch;
    }

    public void setTrans_branch(String trans_branch) {
        this.trans_branch = trans_branch;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getPage_total() {
        return page_total;
    }

    public void setPage_total(String page_total) {
        this.page_total = page_total;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public String getPage_num() {
        return page_num;
    }

    public void setPage_num(String page_num) {
        this.page_num = page_num;
    }

    public String getTrans_oper() {
        return trans_oper;
    }

    public void setTrans_oper(String trans_oper) {
        this.trans_oper = trans_oper;
    }
}
