package project.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Author: Johnny
 * Date: 2017/1/15
 * Time: 19:56
 */
public class TestLoadBalance {
    private static final Map<String, Integer> serverWeightMap = new HashMap<>();

    static {
        serverWeightMap.put("192.168.1.12", 1);
        serverWeightMap.put("192.168.1.13", 1);
        serverWeightMap.put("192.168.1.14", 2);
        serverWeightMap.put("192.168.1.15", 2);
        serverWeightMap.put("192.168.1.16", 3);
        serverWeightMap.put("192.168.1.17", 3);
        serverWeightMap.put("192.168.1.18", 1);
        serverWeightMap.put("192.168.1.19", 2);
    }

    private Integer pos = 0;

    /*round*/
    public String roundRobin() {
        //create new map to avoid 由於服務器上線和下線導致的並發問題
        Map<String, Integer> serverMap = new HashMap<>(serverWeightMap);
        //get ip list
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<>(keySet);

        String server;
        synchronized (pos) {
            if (pos >= keySet.size()) {
                pos = 0;
            }
            server = keyList.get(pos);
            pos++;
        }
        return server;
    }

    /*weight random round*/
    public static String weightRandom() {
        Map<String, Integer> serverMap = new HashMap<>(serverWeightMap);
        Set<String> keySet = serverMap.keySet();
        Iterator<String> it = keySet.iterator();

        List<String> serverList = new ArrayList<>();

        while (it.hasNext()) {
            String server = it.next();
            Integer weight = serverMap.get(server);
            for (int i = 0; i < weight; i++) {
                serverList.add(server);
            }
        }
        Random random = new Random();
        int randomPos = random.nextInt(serverList.size());
        return serverList.get(randomPos);
    }

    /*ip_hash*/
    public static String ipHash(String remoteIp) {
        Map<String, Integer> serverMap = new HashMap<>(serverWeightMap);
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<>(keySet);

        int hashCode = remoteIp.hashCode();
        int serverListSize = keyList.size();
        int serverPos = hashCode % serverListSize;

        return keyList.get(serverPos);
    }

    /*random*/
    public static String random() {
        Map<String, Integer> serverMap = new HashMap<>(serverWeightMap);
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<>(keySet);

        Random random = new Random();
        int randomPos = random.nextInt(keyList.size());

        return keyList.get(randomPos);
    }

    public static void main(String[] args) {
        String serverIp = weightRandom();
        System.out.println(serverIp);

        serverIp = random();
        System.out.println(serverIp);

        serverIp = ipHash("192.168.1.12");
        System.out.println(serverIp);

        TestLoadBalance robin = new TestLoadBalance();
        for (int i = 0; i < 20; i++) {
            serverIp = robin.roundRobin();
            System.out.println(serverIp);
        }
    }
}
