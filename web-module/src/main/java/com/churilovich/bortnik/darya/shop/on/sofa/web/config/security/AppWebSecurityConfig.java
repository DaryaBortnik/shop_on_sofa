package com.churilovich.bortnik.darya.shop.on.sofa.web.config.security;

import com.churilovich.bortnik.darya.shop.on.sofa.web.config.security.handler.SuccessLoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.churilovich.bortnik.darya.shop.on.sofa.web.constants.RoleValueConstants.ADMINISTRATOR;

@Configuration
@Order(1)
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final SuccessLoginHandler successLoginHandler;
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public AppWebSecurityConfig(UserDetailsService userDetailsService,
                                SuccessLoginHandler successLoginHandler,
                                @Qualifier("webAccessDeniedHandler") AccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.successLoginHandler = successLoginHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasAuthority(ADMINISTRATOR)
                .antMatchers("/login")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .successHandler(successLoginHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf()
                .disable();
    }
}
