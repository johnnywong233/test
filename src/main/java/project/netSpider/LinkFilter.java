package project.netSpider;

/**
 * Created by johnny on 2016/9/16.
 *
 */
public interface LinkFilter {
    boolean accept(String url);
}
