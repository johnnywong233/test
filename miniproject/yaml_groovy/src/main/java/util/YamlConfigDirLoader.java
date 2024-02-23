package util;

import entity.ReportFieldConfig;
import lombok.AllArgsConstructor;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Johnny on 2018/3/3.
 */
@AllArgsConstructor
public class YamlConfigDirLoader {
    private final String dir;

    List<ReportFieldConfig> loadConfigs() {
        File[] files = new File(dir).listFiles();
        return Arrays.stream(files).map(
                file -> {
                    try {
                        String content = StreamUtils.copyToString(new FileInputStream(file), StandardCharsets.UTF_8);
                        return YamlConfigLoader.loadConfig(content);
                    } catch (java.io.IOException e) {
                        System.err.println(e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
        ).collect(Collectors.toList());
    }
}