package concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by johnny on 2016/8/18.
 */
public class NavigableMap {
    //http://ifeve.com/concurrent-collections-6/
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Contact> map;
        map = new ConcurrentSkipListMap<>();
        Thread[] threads = new Thread[25];
        int counter = 0;
        //for these 25 thread(task), assign a upper case letter ass ID
        for (char i = 'A'; i < 'Z'; i++) {
            Task task = new Task(map, String.valueOf(i));
            threads[counter] = new Thread(task);
            threads[counter].start();
            counter++;
        }
        //use join() to wait for the end of thread
        for (int i = 0; i < 25; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Main: Size of the map: %d\n", map.size());
        Map.Entry<String, Contact> element;
        Contact contact;
        element = map.firstEntry();
        contact = element.getValue();
        System.out.printf("Main: First Entry: %s: %s\n", contact.
                getName(), contact.getPhone());

        element = map.lastEntry();
        contact = element.getValue();
        System.out.printf("Main: Last Entry: %s: %s\n", contact.getName(), contact.getPhone());

        //get submap
        System.out.print("Main: Submap from A1996 to B1002: \n");
        ConcurrentNavigableMap<String, Contact> submap = map.subMap("A1996", "B1002");
        do {
            element = submap.pollFirstEntry();
            if (element != null) {
                contact = element.getValue();
                System.out.printf("%s: %s\n", contact.getName(), contact.getPhone());
            }
        } while (element != null);
    }

}

@Data
@AllArgsConstructor
class Contact {
    private String name;
    private String phone;
}

@AllArgsConstructor
class Task implements Runnable {
    private final ConcurrentSkipListMap<String, Contact> map;
    private final String id;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Contact contact = new Contact(id, String.valueOf(i + 1000));
            map.put(id + contact.getPhone(), contact);
        }
    }
}