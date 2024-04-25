package com.yevhenii.ticketingService.controller.filter;

import com.yevhenii.ticketingService.config.WebSecurityConfig;
import com.yevhenii.ticketingService.controller.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class ProfileEmptyFilter implements Filter {

    SessionUtils sessionUtils;

    @Autowired
    public ProfileEmptyFilter(SessionUtils sessionUtils) {
        this.sessionUtils = sessionUtils;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        String uri = request.getRequestURI();
        boolean availableForUnauthorized = Arrays.stream(WebSecurityConfig.WHITELIST)
                .anyMatch(pattern -> antPathMatcher.match(pattern, uri));
        chain.doFilter(request, servletResponse);
    }

}
