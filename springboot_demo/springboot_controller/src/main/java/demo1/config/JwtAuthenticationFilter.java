package demo1.config;

import demo1.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static demo1.util.JwtUtil.HEADER_STRING;

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
                String token = request.getHeader(HEADER_STRING);
                JwtUtil.validateToken(token);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
