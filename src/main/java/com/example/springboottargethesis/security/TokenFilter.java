package com.example.springboottargethesis.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.*;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenFilter implements Filter{
    private static final String VALID_TOKEN = "validation-token-123";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        String path = httpRequest.getRequestURI();
        if (path.contains("/auth") ||
                path.contains("/swagger") ||
                path.contains("/swagger-ui") ||
                path.contains("/v3/api-docs") ||
                path.contains("/webjars") ||
                path.contains("/health") ||
                path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.equals("/tasks") && httpRequest.getMethod().equals("GET")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.equals("Bearer " + VALID_TOKEN)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request,response);
    }
}
