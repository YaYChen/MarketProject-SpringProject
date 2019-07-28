package com.springboot.project.authenticate;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JwtFilter implements Filter {
    private JwtHelper jwtHelper;
    private List<String> urls = null;
    private static final org.springframework.util.PathMatcher pathMatcher = new AntPathMatcher();
    public JwtFilter(JwtHelper jwtHelper, String[] authorisedUrls) {
        this.jwtHelper = jwtHelper;
        urls = new ArrayList<String>(Arrays.asList(authorisedUrls));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            if (httpRequest.getHeader("Access-Control-Request-Method") != null
                    && "OPTIONS".equals(httpRequest.getMethod())) {// CORS "pre-flight" request
                httpResponse.addHeader("Access-Control-Max-Age", "7200");
                httpResponse.setStatus(200);
                httpResponse.getWriter().write("OK");
                return;
            }
            String spath = httpRequest.getServletPath();
            //验证受保护的接口
            for (String url : urls) {
                if (pathMatcher.match(url, spath)) {
                    Object token = jwtHelper.validateTokenAndGetClaims(httpRequest);
                    if (token != null) {
                        chain.doFilter(request, response);
                        return;
                    }else{
                        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                        httpResponse.getWriter().write("UNAUTHORIZED");
                        return;
                    }
                }else{
                    chain.doFilter(request, response);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {

    }
}
