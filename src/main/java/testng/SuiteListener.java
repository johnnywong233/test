package testng;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.xml.XmlSuite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wajian on 2016/10/9.
 */
public class SuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
    	//TODO: XML file
    	//TODO: 能不能不要每个listener都写一个XML config file？
        System.out.println("Start suite " + suite.getName());
        XmlSuite xmlSuite = suite.getXmlSuite();
        if (!xmlSuite.getTests().isEmpty()) {
            Map<String, String> parms = new HashMap<>();
            parms.put("ui", "web");
            System.out.println("Set ui param value");
            xmlSuite.setParameters(parms);
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("Finish suite " + suite.getName());
    }
}
