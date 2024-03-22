package mongodb;

import jakarta.annotation.Resource;
import mongodb.model.PrimaryMongoObject;
import mongodb.model.SecondaryMongoObject;
import mongodb.model.repository.PrimaryRepository;
import mongodb.model.repository.SecondaryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 18:39
 */
@SpringBootTest
public class MultiDatabaseTest {
    @Resource
    private PrimaryRepository primaryRepository;
    @Resource
    private SecondaryRepository secondaryRepository;

    @Test
    public void TestSave() {
        System.out.println("Test begin.");
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
        System.out.println("Test complete!");
    }
}
