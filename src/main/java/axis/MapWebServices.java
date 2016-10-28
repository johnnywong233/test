package axis;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.SOAPHeaderElement;

/**
 * Created by johnny on 2016/10/6.
 *
 */
public class MapWebServices {
    //http://www.mincoder.com/article/3226.shtml
    public static void main(String[] args) {
        //TODO
        String soapAction = "http://xxxx.xxxx.xxx/xsd";
        String endpoint = "http://xx.xxx.xx.xxx:8080/XxxXxx/services/xxxs";
        Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            call.setTimeout(60000);
            call.setTargetEndpointAddress(new URL(endpoint));
            call.setOperationName(new QName(soapAction, "getXxxx"));
            SOAPHeaderElement headerElement = new SOAPHeaderElement(soapAction, "AuthHeaderCS");
            headerElement.setNamespaceURI(soapAction);
            headerElement.addChildElement("username").setValue("xxxx");
            headerElement.addChildElement("application").setValue("Xxxx");
            headerElement.addChildElement("department").setValue("TestDept");
            call.addHeader(headerElement);
            call.setReturnType(XMLType.XSD_STRING);
            call.addParameter("o", XMLType.XSD_STRING, ParameterMode.IN);
            String ret = (String) call.invoke(new Object[] { null });
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
