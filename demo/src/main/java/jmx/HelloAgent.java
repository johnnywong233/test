//package jmx;
//
//import com.sun.jdmk.comm.HtmlAdaptorServer;
//
//import javax.management.InstanceAlreadyExistsException;
//import javax.management.MBeanRegistrationException;
//import javax.management.MBeanServer;
//import javax.management.MBeanServerFactory;
//import javax.management.MalformedObjectNameException;
//import javax.management.NotCompliantMBeanException;
//import javax.management.ObjectName;
//
//public class HelloAgent {
//	//see all the JMX bean in http://localhost:8082/
//    public static void main(String args[]) throws MalformedObjectNameException, InstanceAlreadyExistsException,
//            MBeanRegistrationException, NotCompliantMBeanException {
//		MBeanServer server = MBeanServerFactory.createMBeanServer();
////        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
//        ObjectName helloName = new ObjectName("Johnny:name=HelloWorld");//Key properties cannot be empty
//        server.registerMBean(new Hello(), helloName);
//        ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
//        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//        server.registerMBean(adapter, adapterName);
//        adapter.start();
//        System.out.println("start.....");
//    }
//}
