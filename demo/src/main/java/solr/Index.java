package solr;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Arrays;

/**
 * <b>function:</b> JavaEntity Bean; Index需要添加相关的Annotation注解，便于告诉solr哪些属性参与到index中
 * Created by johnny on 2016/10/4.
 */
@Data
public class Index {
    //@Field setter方法上添加Annotation也是可以的
    private String id;
    @Field
    private String name;
    @Field
    private String manu;
    @Field
    private String[] cat;
    @Field
    private String[] features;
    @Field
    private float price;
    @Field
    private int popularity;
    @Field
    private boolean inStock;
    @Field
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id + "#" + this.name + "#" + this.manu + "#" + Arrays.toString(this.cat);
    }

}
