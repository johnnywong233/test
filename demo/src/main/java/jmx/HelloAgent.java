package jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class HelloAgent {
	public static void main(String args[]) throws MalformedObjectNameException, InstanceAlreadyExistsException,
	MBeanRegistrationException, NotCompliantMBeanException{
//		MBeanServer server = MBeanServerFactory.createMBeanServer();
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		ObjectName helloName = new ObjectName("Johnny:name=HelloWorld");//Key properties cannot be empty
		server.registerMBean(new Hello(), helloName);
		ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		server.registerMBean( adapter,  adapterName);
		adapter.start();
		System.out.println("start.....");
	}
}
