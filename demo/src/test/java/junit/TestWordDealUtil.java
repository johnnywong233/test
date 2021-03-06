package junit;

import org.junit.Assert;
import org.junit.Test;

public class TestWordDealUtil {
    @Test
    //https://www.ibm.com/developerworks/cn/java/j-lo-junit4/
    public void wordFormat4DBNormal() {
        String target = "employeeInfo";
        String result = WordDealUtil.wordFormat4DB(target);
        Assert.assertEquals("employee_info", result);
    }

    @Test(expected=NullPointerException.class)
    public void wordFormat4DBNull() {
        String target = null;
        String result = WordDealUtil.wordFormat4DB(target);
        Assert.assertNull(result);
    }

    @Test
    public void wordFormat4DBEmpty() {
        String target = "";
        String result = WordDealUtil.wordFormat4DB(target);
        Assert.assertEquals("", result);
    }

    @Test
    public void wordFormat4DBegin() {
        String target = "EmployeeInfo";
//        String result = WordDealUtil.wordFormat4DB(target);//fail
        String result1 = WordDealUtil.wordFormat4DB_1(target);
//        Assert.assertEquals("employee_info", result);
        Assert.assertEquals("employee_info", result1);
    }

    @Test
    public void wordFormat4DBEnd() {
        String target = "employeeInfoA";
        String result = WordDealUtil.wordFormat4DB(target);
        Assert.assertEquals("employee_info_a", result);
    }

    @Test
    public void wordFormat4DBTogether() {
        String target = "employeeAInfo";
        String result = WordDealUtil.wordFormat4DB(target);
        Assert.assertEquals("employee_a_info", result);
    }


}
