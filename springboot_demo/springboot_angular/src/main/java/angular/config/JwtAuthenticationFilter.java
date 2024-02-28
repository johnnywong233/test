package angular.config;

import angular.util.JwtUtil;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 0:52
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();
    private final String protectUrlPattern;

    public JwtAuthenticationFilter(String protectUrlPattern) {
        this.protectUrlPattern = protectUrlPattern;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            if (PATH_MATCHER.match(protectUrlPattern, request.getServletPath())) {
                request = JwtUtil.validateTokenAndAddUserIdToHeader(request);
            }
        } catch (Exception e) {
            logger.error("unauthorized" + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
