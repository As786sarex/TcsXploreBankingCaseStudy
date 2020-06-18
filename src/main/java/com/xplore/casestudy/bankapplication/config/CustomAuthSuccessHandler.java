package com.xplore.casestudy.bankapplication.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.xplore.casestudy.bankapplication.config.AuthConfiguration.*;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain,
                                        Authentication authentication)
            throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException, ServletException {
        String redirectUrl = null;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (ROLE_EXECUTIVE.equals(authority.getAuthority())
                    || ROLE_ADMIN.equals(authority.getAuthority())) {
                redirectUrl = "/customer/create_customer";
                break;
            } else if (ROLE_CASHIER.equals(authority.getAuthority())) {
                redirectUrl = "/accounts/search_account";
                break;
            }
        }
        if (redirectUrl == null)
            throw new IllegalStateException();
        new DefaultRedirectStrategy().sendRedirect(httpServletRequest,
                httpServletResponse, redirectUrl);
    }
}
