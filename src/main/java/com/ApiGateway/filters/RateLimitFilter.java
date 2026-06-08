package com.ApiGateway.filters;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RateLimitFilter implements Filter {

    private final RateLimiter limiter;

    public RateLimitFilter(
            RateLimiterRegistry registry
    ) {

        this.limiter =
                registry.rateLimiter(
                        "gatewayLimiter"
                );

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        HttpServletResponse res =
                (HttpServletResponse) response;

        String path =
                req.getRequestURI();

        /*
          limitar solamente APIs
        */

        if(path.startsWith("/api")){

            if(!limiter.acquirePermission()){

                res.setStatus(429);

                res.setContentType(
                        "application/json"
                );

                res.getWriter().write(
                        """
                        {
                          "status":429,
                          "error":"Too many requests"
                        }
                        """
                );

                return;

            }

        }

        chain.doFilter(
                request,
                response
        );

    }

}