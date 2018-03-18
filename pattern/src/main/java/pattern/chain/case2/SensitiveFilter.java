package pattern.chain.case2;

/**
 * Created by Johnny on 2018/3/18.
 * 定义的过滤敏感字眼的过滤规则
 */
public class SensitiveFilter implements Filter {
    public void doFilter(Request request, Response response, FilterChain chain) {
        request.request = request.request.replace("被就业", "就业").replace("敏感", "") + " ---sensitiveFilter()";
        chain.doFilter(request, response, chain);
        response.response += "---sensitiveFilter()";
    }
}