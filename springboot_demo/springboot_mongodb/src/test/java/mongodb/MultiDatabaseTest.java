package mongodb;

import mongodb.model.PrimaryMongoObject;
import mongodb.model.SecondaryMongoObject;
import mongodb.model.repository.PrimaryRepository;
import mongodb.model.repository.SecondaryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 18:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiDatabaseTest {
    @Autowired
    private PrimaryRepository primaryRepository;

    @Autowired
    private SecondaryRepository secondaryRepository;

    @Test
    public void TestSave() {

        System.out.println("************************************************************");
        System.out.println("Test begin.");
        System.out.println("************************************************************");

        this.primaryRepository.save(new PrimaryMongoObject(null, "database1"));

        this.secondaryRepository.save(new SecondaryMongoObject(null, "database2"));

        List<PrimaryMongoObject> primaries = this.primaryRepository.findAll();
        for (PrimaryMongoObject primary : primaries) {
            System.out.println(primary.toString());
        }

        List<SecondaryMongoObject> secondaries = this.secondaryRepository.findAll();

        for (SecondaryMongoObject secondary : secondaries) {
            System.out.println(secondary.toString());
        }

        System.out.println("************************************************************");
        System.out.println("Test complete!");
        System.out.println("************************************************************");
    }
}
