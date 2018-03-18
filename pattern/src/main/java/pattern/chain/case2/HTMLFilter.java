package pattern.chain.case2;

/**
 * Created by Johnny on 2018/3/18.
 * 处理字符串中的HTML标记
 */
public class HTMLFilter implements Filter {
    public void doFilter(Request request, Response response, FilterChain chain) {
        request.request = request.request.replace('<', '[').replace('>', ']') + "----HTMLFilter()";
        chain.doFilter(request, response, chain);
        response.response += "---HTMLFilter()";
    }
}