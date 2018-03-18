package pattern.chain.case2;

/**
 * Created by Johnny on 2018/3/18.
 * 定义接口Filter，具体的过滤规则需要实现这个接口
 */
public interface Filter {
    void doFilter(Request request, Response response, FilterChain chain);
}