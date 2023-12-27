package vn.huydtg.immunizationservice.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.huydtg.immunizationservice.security.SecurityUtils;

import java.io.IOException;

@Component
public class UserActivityFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(UserActivityFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isEnable = SecurityUtils.getIsEnable();
        if(!isEnable) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"account_is_inactive\", \"path\": \"/error\"}");
            logger.info(SecurityUtils.getIsEnable().toString());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
