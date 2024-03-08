package util;

import entity.ReportFieldConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Johnny on 2018/3/3.
 */
@Slf4j
public class FieldsConfigLoader {
    private static final Map<String, ReportFieldConfig> fieldConfigMap = new HashMap<>();

    static {
        try {
            //use hard code absolute path is not a good idea
            List<ReportFieldConfig> fieldConfigs = new YamlConfigDirLoader("scripts").loadConfigs();
            fieldConfigs.forEach(
                    fc -> fieldConfigMap.put(fc.getName(), fc)
            );
            log.info("fieldConfigs: {}.", fieldConfigs);
        } catch (Exception ex) {
            log.error("failed to load fields conf: ", ex);
        }
    }

    public static ReportFieldConfig getFieldConfig(String name) {
        return fieldConfigMap.get(name);
    }
}
