package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Created by johnny on 2016/10/6.
 *
 */
public class Discovery {
    PrintStream out = null;

    public Discovery(){
    }

    public Discovery(PrintStream out){
        this.out = out;
    }

    /**
     * 扫描端口
     * https://my.oschina.net/tangcoffee/blog/330223
     */
    @Test
    static void t2(){
        new SystoolkitConfigure().run();
        Discovery d = new Discovery(System.out);
		d.scanPort("192.168.2", 1, 254, "22,23,80");
        List<String> ips = new ArrayList<>();
        ips.add("192.168.2.22");
        ips.add("192.168.2.23");
        ips.add("192.168.2.54");
        ips.add("192.168.2.55");
        d.scanPort(ips,"22,23,80,100-200");
    }

    /**
     * ping扫描
     */
    //TODO
    @Test
    static void t1(){
        new SystoolkitConfigure().run();
        Discovery d = new Discovery(System.out);
        Map<String,Boolean> ret = d.ping("192.168.2");
		System.out.println(ret);
        List<String> ips = new ArrayList<String>();
        ips.add("192.168.2.22");
        ips.add("192.168.2.23");
        ips.add("192.168.2.54");
        ips.add("192.168.2.55");
        ret = d.ping(ips);
    }

    /**
     * 对指定的ip(数组)进行ping探测
     * @param ips ip数组
     * @return 返回格式为 ip:是否成功
     */
    public LinkedHashMap<String, Boolean> ping(List<String> ips) {
        LinkedHashMap<String, Boolean> ret = new LinkedHashMap<>();
        Map<String, String> args = new HashMap<>();
        BufferedReader br;
        for (String ip : ips) {
            args.put("ip", ip);
            String value = SystoolkitConfigure.getValue("ping.command", args);
            // 获得JVM的运行环境
//			Runtime r = Runtime.getRuntime();
            // 创建一个ping命令进程
            try {
                Process p = Runtime.getRuntime().exec(value);
                StringBuilder sb = new StringBuilder();
                String line;

                br = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
                while ((line = br.readLine()) != null) {
                    sb.append(line.trim()).append('\n');
                }
                br.close();

                br = new BufferedReader(new InputStreamReader(
                        p.getErrorStream()));
                while ((line = br.readLine()) != null) {
                    sb.append(line.trim());
                    sb.append('\n');
                }
                br.close();

                String os = SystoolkitConfigure.getOS().toLowerCase();
                if (os.contains("windows")) {
                    int index = sb.toString().indexOf("Packets: Sent = 1, Received = 1,");
                    ret.put(ip, index != -1);
                    if(null!=out) {
                        out.println(ip+":"+(index != -1));
                        out.flush();
                    }
                } else if (os.contains("linux")) {
                    int index = sb.toString().indexOf("1 packets transmitted, 1 received");
                    ret.put(ip, index != -1);
                    if(null!=out) {
                        out.println(ip+":"+(index != -1));
                        out.flush();
                    }
                }
            } catch (IOException e) {
				e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 对指定网段的指定ip范围的机器，进行ping探测
     * @param networkSection
     *            网段，例:192.168.2
     * @param startIp
     *            开始ip,包含此ip,值>0
     * @param endIp
     *            结束ip,包含此ip,值<255
     * @return ping探测结果
     */
    public LinkedHashMap<String, Boolean> ping(String networkSection,
                                               int startIp, int endIp) {
        List<String> ips = new ArrayList<>();
        if (startIp <= 0 || endIp >= 255) {
            throw new IllegalArgumentException("startIp<=0 or endIp>=255.");
        }
        for (int i = startIp; i <= endIp; i++) {
            // ips.add(String.format("%s.%d", networkSection,i));
            ips.add(networkSection + "." + i);
        }
        return ping(ips);
    }

    /**
     * 此方法相当于ping(networkSection,1,254)
     * @param networkSection
     * @return LinkedHashMap
     */
    public LinkedHashMap<String, Boolean> ping(String networkSection) {
        return ping(networkSection, 1, 254);
    }

//	public LinkedHashMap<String, Boolean> scanPort(String networkSection,
//			int startIp, int endIp, String port) {
//		// throw new RuntimeException();
//		LinkedHashMap<String, Boolean> ret = new LinkedHashMap<String, Boolean>();
//		List<Integer> ports = new ArrayList<Integer>();
//		List<String> ips = new ArrayList<String>();
//		if (startIp <= 0 || endIp >= 255) {
//			throw new IllegalArgumentException("startIp<=0 or endIp>=255.");
//		}
//		for (int i = startIp; i <= endIp; i++) {
//			ips.add(networkSection + "." + i);
//		}
//		String[] ss = port.split(",");
//		for (String s : ss) {
//			if (-1 != s.indexOf('-')) {
//				String[] range = s.split("-");
//				for (int i = Integer.parseInt(range[0]), end = Integer
//						.parseInt(range[1]); i <= end; i++) {
//					ports.add(i);
//				}
//			} else {
//				ports.add(Integer.parseInt(s));
//			}
//		}
//		Socket client = null;
//		for (String ip : ips) {
//			for (Integer p : ports) {
//				try {
//					client = new Socket();
//					client.connect(new InetSocketAddress(ip, p), 300);
//					ret.put(ip, true);
//				} catch (Exception ex) {
//					ret.put(ip, false);
//				} finally {
//					try {
//						if (null != client)
//							client.close();
//					} catch (Exception ex) {
//					}
//				}
//			}
//
//		}
//		return ret;
//	}

//	public void scanPort(List<String> ips, String port, PrintStream out) {
//		List<Integer> ports = new ArrayList<Integer>();
//
//		String[] ss = port.split(",");
//		for (String s : ss) {
//			if (-1 != s.indexOf('-')) {
//				String[] range = s.split("-");
//				for (int i = Integer.parseInt(range[0]), end = Integer
//						.parseInt(range[1]); i <= end; i++) {
//					ports.add(i);
//				}
//			} else {
//				ports.add(Integer.parseInt(s));
//			}
//		}
//
//		Socket client = null;
//		for (String ip : ips) {
//			for (Integer p : ports) {
//				try {
//					client = new Socket();
//					client.connect(new InetSocketAddress(ip, p), 300);
//					// ret.add(ip+":"+p);
//					out.println(ip + ":" + p);
//					out.flush();
//				} catch (Exception ex) {
//				} finally {
//					try {
//						if (null != client)
//							client.close();
//					} catch (Exception ex) {
//					}
//				}
//			}
//		}
//
//		out.close();
//	}

    public List<String> scanPort(String networkSection,
                                 int startIp, int endIp, String port) {
        List<String> ips = new ArrayList<>();
        if (startIp <= 0 || endIp >= 255) {
            throw new IllegalArgumentException("startIp<=0 or endIp>=255.");
        }
        for (int i = startIp; i <= endIp; i++) {
            ips.add(networkSection + "." + i);
        }
        return scanPort(ips,port);
    }

    public List<String> scanPort(List<String> ips, String port) {
        List<String> ret = new ArrayList<>();
        List<Integer> ports = new ArrayList<>();

        String[] ss = port.split(",");
        for (String s : ss) {
            if (-1 != s.indexOf('-')) {
                String[] range = s.split("-");
                for (int i = Integer.parseInt(range[0]), end = Integer
                        .parseInt(range[1]); i <= end; i++) {
                    ports.add(i);
                }
            } else {
                ports.add(Integer.parseInt(s));
            }
        }
        Socket client = null;
        for (String ip : ips) {
            for (Integer p : ports) {
                try {
                    client = new Socket();
                    client.connect(new InetSocketAddress(ip, p), 300);
                    ret.add(ip + ":" + p);
                    if(null!=out){
                        out.println(ip + ":" + p);
                        out.flush();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (null != client)
                            client.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return ret;
    }
}