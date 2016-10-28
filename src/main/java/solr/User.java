package solr;

import org.apache.solr.client.solrj.beans.Field;
import java.io.Serializable;

/**
 * Created by wajian on 2016/10/4.
 * java bean to be converted to solr document
 */
public class User implements Serializable{
    private static final long serialVersionUID = 8606788203814942679L;

    //@Field
    private int id;
    @Field
    private String name;
    @Field
    private int age;

    /**
     * 可以给某个属性重命名，likes就是solr index的属性；在solrIndex中将显示like为likes
     */
    @Field("likes")
    private String[] like;
    @Field
    private String address;
    @Field
    private String sex;
    @Field
    private String remark;
    
    public int getId() {
        return id;
    }

    //setter 方法上面也可以
    @Field
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.id + "#" + this.name + "#" + this.age + "#" + this.like + "#" + this.address + "#" + this.sex + "#" + this.remark;
    }



    public void setAge(int age) {
        this.age = age;
    }

    public void setLike(String[] like) {
        this.like = like;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}