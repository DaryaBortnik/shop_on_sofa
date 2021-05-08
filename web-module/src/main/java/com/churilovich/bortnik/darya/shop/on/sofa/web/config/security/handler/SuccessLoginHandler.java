package com.churilovich.bortnik.darya.shop.on.sofa.web.config.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.churilovich.bortnik.darya.shop.on.sofa.web.constants.RoleValueConstants.ADMINISTRATOR;
import static com.churilovich.bortnik.darya.shop.on.sofa.web.constants.RoleValueConstants.CUSTOMER_USER;
import static com.churilovich.bortnik.darya.shop.on.sofa.web.constants.RoleValueConstants.SALE_USER;

@Component
public class SuccessLoginHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        handle(httpServletResponse, authentication);
    }

    private void handle(HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            switch (authority.getAuthority()) {
                case ADMINISTRATOR:
                    httpServletResponse.sendRedirect("/admin/users");
                    break;
                case CUSTOMER_USER:
                    httpServletResponse.sendRedirect("/user/customer");
                    break;
                case SALE_USER:
                    httpServletResponse.sendRedirect("/user/sale");
                    break;
                default:
                    httpServletResponse.sendRedirect("/login");
            }
        }
    }
}

