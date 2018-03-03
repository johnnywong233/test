package util;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.yamlbeans.YamlReader;
import entity.ReportFieldConfig;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Johnny on 2018/3/3.
 */
public class YamlConfigLoader {
    static ReportFieldConfig loadConfig(String content) {
        try {
            YamlReader reader = new YamlReader(content);
            Object object = reader.read();
            return JSON.parseObject(JSON.toJSONString(object), ReportFieldConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Load config failed:" + content, e);
        }
    }

    public static List<ReportFieldConfig> loadConfigs(List<String> contents) {
        return contents.stream().map(YamlConfigLoader::loadConfig).collect(Collectors.toList());
    }
}
