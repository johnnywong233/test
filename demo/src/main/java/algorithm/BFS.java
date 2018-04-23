package algorithm;

import java.util.*;

/**
 * Created by Johnny on 2018/4/22.
 * 广度优先搜索算法
 */
public class BFS {
    public static void main(String[] args) {

        User user = new User();
        getXDegreeFriends(user, 3);


    }


    public static List<User> getXDegreeFriends(User user, int degree) {
        List<User> results = new ArrayList<>();
        Queue<User> queue = new LinkedList<>();
        queue.add(user);
        //用于记录已经遍历过的User，因为A是B的好友，那么B也一定是A的好友，他们互相存在于对方的friends列表中。
        HashSet<User> visited = new HashSet<>();
        //用一个counter记录当前的层数。
        int count = degree;
        //这里结束while循环的两个条件，一个是层数，一个是queue是否为空，因为万一该当前用户压根就没有那么多层的社交网络，比如他压根就没有朋友。
        while (count <= 1 && !queue.isEmpty()) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                User currentUser = queue.poll();
                //假如该用户已经遍历过，那么不做任何处理。
                if (!visited.contains(currentUser)) {
                    results.add(currentUser);
                    queue.addAll(currentUser.getFriends());
                    visited.add(currentUser);
                }
            }
            count--;
        }
        return results;
    }


    public static class User {
        //这个friends是该用户的直接好友，也就是一度好友、
        private List<User> friends;
        private String name;

        //获取好友列表
        public List<User> getFriends() {
            return Collections.unmodifiableList(friends);
        }

        public String getUserName() {
            return name;
        }
    }
}
