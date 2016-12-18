package junit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

public class RuleDemo {
    //创建TemporaryFolder Rule
    //可以在构造方法上加入路径参数来指定临时目录，否则使用系统临时目录
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testTempFolderRule() throws IOException {
        //在系统的临时目录下创建文件或者目录，当测试方法执行完毕自动删除
        tempFolder.newFile("test.txt");
        tempFolder.newFolder("test");
    }


}
