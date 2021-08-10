package com.techmojo.ratelimiter.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class RateLimitFilter implements Filter {
    private final RequestTimingUtility requestTimingUtility;

    RateLimitFilter(RequestTimingUtility requestTimingUtility) {
        this.requestTimingUtility = requestTimingUtility;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String key = httpServletRequest.getHeader("TenantId");
        //based on the header parameter we just deal with leftOverValid time of the Request.
        //if it is 0, then the request is valid
        //or else it is the time remaining before we can validly call the API
        long remainingTimeForARequest = requestTimingUtility.isValidRequest(key);
        if (remainingTimeForARequest != 0) {
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpServletResponse.getWriter().write("Too many requests. Remaining Time in seconds: " + remainingTimeForARequest);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}