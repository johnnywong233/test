package fm;

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.nio.file.Paths;

/**
 * Created by Johnny on 2018/3/4.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DemoApplicationTests {

    @Test
    public void generateAsciiDocs() throws Exception {
        //输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();

        //http://localhost:8080/v2/api-docs 有内容啊
        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("src/docs/asciidoc/generated"));
    }

    @Test
    public void generateMarkdownDocs() throws Exception {
        //输出 markdown 格式
        Swagger2MarkupConfig config1 = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config1)
                .build()
                .toFolder(Paths.get("src/docs/markdown/generated"));
    }

    @Test
    public void generateConfluenceDocs() throws Exception {
        //输出 confluence 格式
        Swagger2MarkupConfig config2 = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config2)
                .build()
                .toFolder(Paths.get("src/docs/confluence/generated"));
    }

    @Test
    public void generateAsciiDocsToFile() throws Exception {
        //	输出Ascii到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("src/docs/asciidoc/generated/all"));
    }

    @Test
    public void generateMarkdownDocsToFile() throws Exception {
        //	输出Markdown到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("src/docs/markdown/generated/all"));
    }

}