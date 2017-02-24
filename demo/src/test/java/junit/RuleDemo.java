package junit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

public class RuleDemo {
    //TemporaryFolder Rule
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testTempFolderRule() throws IOException {
        tempFolder.newFile("test.txt");
        tempFolder.newFolder("test");
    }


}
