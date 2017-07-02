package sql.or.manager;

import sql.or.CDCEvent;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:27
 */
public class CDCEventManager {
    public static final ConcurrentLinkedDeque<CDCEvent> queue = new ConcurrentLinkedDeque<>();
}
