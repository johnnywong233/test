package drools;

import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnny on 2018/4/6.
 */
public class RuleEngineImpl implements RuleEngine {

    private RuleBase ruleBase;

    @Override
    public void init() {
        /* Sets the system time format. */
        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
        /* Get the base factory. */
        ruleBase = SingleRuleFactory.getRuleBase();
        try {
            /* Get the rule files has bean read. */
            PackageBuilder packageBuilder = getPackageBuilderFile();
            /* Add the package to the 'RuleBase'. */
            ruleBase.addPackages(packageBuilder.getPackages());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {
        ruleBase = SingleRuleFactory.getRuleBase();
        Package[] packages = ruleBase.getPackages();
        for (Package items : packages) {
            ruleBase.removePackage(items.getName());
        }
        init();
    }


    @Override
    public void execute(final EntityRule entityRule) {
        if (null == ruleBase.getPackages() || 0 == ruleBase.getPackages().length) {
            return;
        }
        StatefulSession statefulSession = ruleBase.newStatefulSession();
        statefulSession.insert(entityRule);
        statefulSession.fireAllRules(activation -> !activation.getRule().getName().contains("_test"));
        statefulSession.dispose();
    }

    /**
     * Read the rule files.
     */
    private PackageBuilder getPackageBuilderFile() throws Exception {
        /* Get the rule files. */
        List<String> drlFilePath = getRuleFile();
        /* Sets the file to 'readers'. */
        List<Reader> readers = loadRuleFile(drlFilePath);
        /* To create the 'backageBuilder'. */
        PackageBuilder packageBuilder = new PackageBuilder();
        for (Reader r : readers) {
            packageBuilder.addPackageFromDrl(r);
        }
        /* Check if the script has a problem. */
        if (packageBuilder.hasErrors()) {
            throw new Exception(packageBuilder.getErrors().toString());
        }
        return packageBuilder;
    }

    /**
     * Load the script files.
     */
    private List<Reader> loadRuleFile(List<String> drlFilePath) throws FileNotFoundException {
        if (null == drlFilePath || 0 == drlFilePath.size()) {
            return null;
        }
        List<Reader> readers = new ArrayList<>();
        for (String ruleFilePath : drlFilePath) {
            readers.add(new FileReader(new File(ruleFilePath)));
        }
        return readers;
    }

    /**
     * Get the rule files.
     */
    private List<String> getRuleFile() {
        List<String> drlFilePath = new ArrayList<>();
        //fixme
        String path = "/Users/wangjian/GitHub/test/demo/src/main/resources/rules/drools.drl";
        drlFilePath.add(path);
        return drlFilePath;
    }
}
