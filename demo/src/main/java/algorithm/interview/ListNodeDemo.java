package algorithm.interview;

import java.util.HashMap;

/**
 * 关于链表
 *
 * @author johnny
 */
public class ListNodeDemo {

    public static void main(String[] args) {

    }

    /**
     * 链表翻转
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next; // 临时保存下一个节点
            current.next = prev; // 反转当前节点的指针
            prev = current; // 移动 prev 指针
            current = next; // 移动 current 指针
        }
        return prev; // prev 最终指向反转后的新头结点
    }

    /**
     * 归并排序实现链表的排序：将链表递归地拆分成两个子链表，分别对其排序，然后将排序后的子链表合并成一个有序链表
     */
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 使用快慢指针找到链表的中间点
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // 断开链表
        prev.next = null;
        // 递归排序左右部分
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);
        // 合并两个有序链表
        return merge(l1, l2);
    }

    /**
     * 求链表倒数第k个节点
     */
    public static ListNode getKthFromEnd(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;
        // 让快指针先移动 k 步
        for (int i = 0; i < k; i++) {
            if (fast == null) {
                // 如果链表长度小于 k，返回 null
                return null;
            }
            fast = fast.next;
        }
        // 快慢指针一起移动，直到快指针到达链表末尾
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow; // 返回慢指针所指的节点
    }

    /**
     * 求链表中间节点
     */
    public static ListNode middleNode(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        // 快指针每次移动两步，慢指针每次移动一步
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 返回慢指针所指的节点，即为链表的中间节点
        return slow;
    }

    /**
     * 判断给定单向链表是否有环
     */
    public static boolean hasLoop(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (true) {
            if (fast == null || fast.next == null) {
                // fast走到链表尾
                return false;
            } else if (fast.next == slow || fast == slow) {
                return true;
            } else {
                slow = slow.next;
                fast = fast.next.next;
            }
        }
    }

    /**
     * 两个链表的第一个公共结点
     */
    public static ListNode findFirstCommonNode(ListNode current1, ListNode current2) {
        HashMap<ListNode, Integer> hashMap = new HashMap<>(16);
        while (current1 != null) {
            hashMap.put(current1, null);
            current1 = current1.next;
        }
        while (current2 != null) {
            if (hashMap.containsKey(current2)) {
                return current2;
            } else if (getNoLoopLength(current2) == 1) {
                // 链表2长度为1的特殊情况
                return null;
            }
            current2 = current2.next;
        }
        return null;
    }

    /**
     * 无环单链表长度
     */
    private static int getNoLoopLength(ListNode head) {
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }

    /**
     * 删除排序链表中重复元素
     */
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode current = head;
        // 遍历链表
        while (current.next != null) {
            // 如果当前节点的值等于下一个节点的值
            if (current.val == current.next.val) {
                // 跳过下一个节点
                current.next = current.next.next;
            } else {
                // 移动到下一个节点
                current = current.next;
            }
        }
        return head;
    }

    /**
     * 合并两个有序链表并保持输出的链表是有序
     */
    public static ListNode merge(ListNode l1, ListNode l2) {
        ListNode temp = new ListNode(-1);
        ListNode current = temp;
        // 遍历两个链表，直到其中一个链表为空
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        // 将剩余的节点接到新链表的末尾
        if (l1 != null) {
            current.next = l1;
        } else {
            current.next = l2;
        }
        // 返回新链表的头节点
        return temp.next;
    }

    /**
     * 给定一个链表，每k个节点一组进行翻转，返回翻转后的链表。k为小于或等于链表长度的正整数。如果节点总数不是k的整数倍，最后剩余的节点保持原有顺序。
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        ListNode b = head;
        for (int i = 0; i < k; i++) {
            if (b == null) {
                return head;
            }
            b = b.next;
        }
        ListNode newHead = reverse(head, b);
        head.next = reverseKGroup(b, k);
        return newHead;
    }

    private static ListNode reverse(ListNode a, ListNode b) {
        ListNode pre, cur, nxt;
        pre = null;
        cur = a;
        nxt = a;
        while (nxt != b) {
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        return pre;
    }

    /**
     * 给定一个链表头节点head和一个特定值val，把所有小于val的节点都放置在大于或等于val的节点之前。保留两个分区中每个节点的初始相对位置。
     */
    public static ListNode partition(ListNode head, int x) {
        ListNode tmpHead1 = new ListNode(0);
        ListNode tmpHead2 = new ListNode(0);
        ListNode node1 = tmpHead1;
        ListNode node2 = tmpHead2;
        while (head != null) {
            if (head.val < x) {
                node1.next = head;
                head = head.next;
                node1 = node1.next;
                node1.next = null;
            } else {
                node2.next = head;
                head = head.next;
                node2 = node2.next;
                node2.next = null;
            }
        }
        node1.next = tmpHead2.next;
        return tmpHead1.next;
    }

    /**
     * 删除链表里元素值等于val的节点
     */
    public static ListNode removeElements(ListNode head, int val) {
        ListNode top = new ListNode(0);
        top.next = head;
        ListNode pre = top;
        ListNode temp = head;
        while (temp != null) {
            if (temp.val == val) {
                pre.next = temp.next;
            } else {
                pre = temp;
            }
            temp = temp.next;
        }
        return top.next;
    }

    /**
     * 判断单链表是否为回文链表
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode slow = head;
        ListNode quick = head;
        while (quick != null && quick.next != null) {
            quick = quick.next.next;
            slow = slow.next;
        }
        ListNode pre = null;
        ListNode p = slow;
        while (p != null) {
            ListNode temp = p.next;
            p.next = pre;
            pre = p;
            p = temp;
        }
        while (pre != null) {
            if (pre.val == head.val) {
                pre = pre.next;
                head = head.next;
            } else {
                return false;
            }
        }
        return true;
    }

    private static class ListNode {
        private ListNode next;
        private int val;

        ListNode(int val) {
            this.val = val;
        }
    }
}
