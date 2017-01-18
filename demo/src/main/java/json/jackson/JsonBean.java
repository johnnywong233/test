package json.jackson;

/**
 * Author: Johnny
 * Date: 2017/1/3
 * Time: 21:28
 */
public class JsonBean {
    private String custNo;
    private String custName;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustEm(String custName) {
        this.custName = custName;
    }
}
