package drools;

import lombok.Data;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * Created by Johnny on 2018/4/6.
 */
public class DroolsTest {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        //不是 "/rules/user.drl"
        builder.add(ResourceFactory.newClassPathResource("rules/user.drl"), ResourceType.DRL);
        if (builder.hasErrors()) {
            System.out.println("规则错误：");
            for (KnowledgeBuilderError error : builder.getErrors()) {
                System.out.println(error);
            }
            return;
        }
        KnowledgeBase kb = KnowledgeBaseFactory.newKnowledgeBase();
        kb.addKnowledgePackages(builder.getKnowledgePackages());
        StatefulKnowledgeSession s = kb.newStatefulKnowledgeSession();
        User user = new User();
        user.setMoney(50);
        s.insert(user);
        s.fireAllRules();
        s.dispose();
    }

    @Data
    public static class User {
        private int money; // 手中的钱
        private int kp; // 空瓶数
        private int totals; // 喝掉的瓶数
    }
}
