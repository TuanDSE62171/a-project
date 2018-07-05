package portal_xml.portal_xml.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class CrawlerTokenRequestFilter implements Filter {

    private String token;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        token = (String) filterConfig.getServletContext().getAttribute("TOKEN");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestToken = servletRequest.getParameter("token");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (requestToken.equals(token) && request.getMethod().equals("POST")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.setHeader("WWW-Authenticate", "CustomTokenAuth realm=\"Access to crawlers management\"");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    }


    @Override
    public void destroy() {
        return;
    }
}
