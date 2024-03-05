package batch.config;

import batch.StudentItemProcessor;
import batch.entity.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 18:49
 */
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class BatchConfig1 {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private StudentItemProcessor processor;

    //执行流程： csv -> txt -> xml
    @Bean
    public FlatFileItemReader<Student> csvItemReader() {
        FlatFileItemReader<Student> csvItemReader = new FlatFileItemReader<>();
        csvItemReader.setResource(new ClassPathResource("data/sample-data.csv"));
        csvItemReader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("name", "age");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(Student.class);
            }});
        }});
        return csvItemReader;
    }

    @Bean
    public FlatFileItemReader<Student> txtItemReader() {
        FlatFileItemReader<Student> txtItemReader = new FlatFileItemReader<>();
        txtItemReader.setResource(new ClassPathResource("data/sample-data.txt"));
        txtItemReader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("age", "name");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(Student.class);
            }});
        }});
        return txtItemReader;
    }

    @Bean
    public FlatFileItemWriter<Student> txtItemWriter() {
        FlatFileItemWriter<Student> txtItemWriter = new FlatFileItemWriter<>();
        txtItemWriter.setAppendAllowed(true);
        txtItemWriter.setEncoding("UTF-8");
        txtItemWriter.setResource(new ClassPathResource("/data/sample-data.txt"));
        txtItemWriter.setLineAggregator(new DelimitedLineAggregator<>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                setNames(new String[]{"name", "age"});
            }});
        }});
        return txtItemWriter;
    }

    @Bean
    public StaxEventItemWriter<Student> xmlItemWriter() {
        StaxEventItemWriter<Student> xmlItemWriter = new StaxEventItemWriter<>();
        xmlItemWriter.setRootTagName("root");
        xmlItemWriter.setSaveState(true);
        xmlItemWriter.setEncoding("UTF-8");
        xmlItemWriter.setResource(new ClassPathResource("/data/sample-data.xml"));
        xmlItemWriter.setMarshaller(new XStreamMarshaller() {{
            Map<String, Class<Student>> map = new HashMap<>();
            map.put("Student", Student.class);
            setAliases(map);
        }});
        return xmlItemWriter;
    }

    @Bean
    public Job flatFileJob(Step stepCsv2Txt, Step stepTxt2Xml) {
        return jobBuilderFactory.get("flatFileJob")
                .incrementer(parameters -> {
                    assert parameters != null;
                    Map<String, JobParameter> parameterMap = parameters.getParameters();
                    parameterMap.put("key", new JobParameter(UUID.randomUUID().toString()));
                    return parameters;
                })
                .start(stepCsv2Txt)
                .next(stepTxt2Xml)
                .build();
    }

    @Bean
    public Step stepCsv2Txt(ItemReader<Student> csvItemReader, ItemWriter<Student> txtItemWriter) {
        return stepBuilderFactory.get("stepCsv2Txt")
                .<Student, Student>chunk(10)
                .reader(csvItemReader)
                .processor(processor)
                .writer(txtItemWriter)
                .build();
    }

    @Bean
    public Step stepTxt2Xml(ItemReader<Student> txtItemReader, ItemWriter<Student> xmlItemWriter) {
        return stepBuilderFactory.get("stepTxt2Xml")
                .<Student, Student>chunk(10)
                .reader(txtItemReader)
                .processor(processor)
                .writer(xmlItemWriter)
                .build();
    }
}
