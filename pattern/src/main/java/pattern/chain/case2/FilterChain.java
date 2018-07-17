package pattern.chain.case2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnny on 2018/3/18.
 */
public class FilterChain implements Filter {
    //用List集合来存储过滤规则
    private List<Filter> filters = new ArrayList<>();
    //用于标记规则的引用顺序
    private int index = 0;

    //往规则链条中添加规则
    FilterChain addFilter(Filter f) {
        filters.add(f);
        //Chain链添加过滤规则结束后返回添加后的Chain，方便我们下面doFilter函数的操作
        return this;
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        //index初始化为0,filters.size()为3，不会执行return操作
        if (index == filters.size()) {
            return;
        }
        //每添加一个过滤规则，index自增1
        Filter f = filters.get(index);
        index++;
        //根据索引值获取对应的规律规则对字符串进行处理
        f.doFilter(request, response, chain);
    }
}