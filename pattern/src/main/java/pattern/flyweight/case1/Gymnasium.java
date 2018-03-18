package pattern.flyweight.case1;

import lombok.Data;

/**
 * Created by Johnny on 2018/3/18.
 */
@Data
public class Gymnasium implements Build {
    private String name;
    private String shape;
    private String sport;

    Gymnasium(String sport) {
        this.setSport(sport);
    }

    @Override
    public void use() {
        System.out.println("该体育馆被使用来召开奥运会" + "  运动为：" + sport + "  形状为：" + shape + "  名称为：" + name);
    }
}
