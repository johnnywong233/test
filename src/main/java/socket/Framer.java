package socket;

import java.io.IOException;
import java.io.OutputStream;

public interface Framer {
	/*
	 * http://blog.csdn.net/ns_code/article/details/14225541
	 * frameMag（）方法用来添加成帧信息并将指定消息输出到指定流
	 * nextMsg（）方法则扫描指定的流，从中抽取出下一条消息。
	 */
	void frameMsg(byte[] message, OutputStream out) throws IOException;  
	byte[] nextMsg() throws IOException;
}
