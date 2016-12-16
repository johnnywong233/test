package solr;

import org.apache.solr.client.solrj.beans.Field;

/**
 * <b>function:</b> JavaEntity Bean; Index需要添加相关的Annotation注解，便于告诉solr哪些属性参与到index中
 * Created by wajian on 2016/10/4.
 */
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

    public String getId() {
        return id;
    }

    @Field
    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return this.id + "#" + this.name + "#" + this.manu + "#" + this.cat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCat(String[] cat) {
        this.cat = cat;
    }

    public void setManu(String manu) {
        this.manu = manu;
    }
}
