package pattern.chain.case2;

/**
 * Created by Johnny on 2018/3/18.
 */
public class FaceFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        request.request = request.request.replace(":):", "^V^") + "----FaceFilter()";
        chain.doFilter(request, response, chain);
        response.response += "---FaceFilter()";
    }
}