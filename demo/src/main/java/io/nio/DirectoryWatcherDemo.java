package io.nio;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * Created by wajian on 2016/8/28.
 */
public class DirectoryWatcherDemo {
	//http://www.phpxs.com/code/1001507/
    public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("C:", "temp").toAbsolutePath();
        //类DirectoryWatcherDemo所在的目录
        //Path this_dir = Paths.get(".");
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
        while(true) {
            WatchKey watchKey = watchService.take();
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("Create " + path.resolve((Path) event.context()).toAbsolutePath());
                } else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("Modify " + path.resolve((Path) event.context()).toAbsolutePath());
                } else {
                    System.out.println("Delete " + path.resolve((Path) event.context()).toAbsolutePath());
                }
            }
            watchKey.reset();

            // Cancel the watch
            // watchKey.cancel();
        }
    }
}
