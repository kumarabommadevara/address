package com.harsha.address.controller.config;

import com.harsha.address.service.CustomerService;
import com.harsha.address.service.CustomerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    public static final String[] ENDPOINTS_WHITELIST = {
            "/css/**",
            "/",
            "/login",
            "/home",
            "/test",
            "/h2-console/**"
    };
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error";
    public static final String DEFAULT_SUCCESS_URL = "/home";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request ->
                        request.antMatchers(ENDPOINTS_WHITELIST).permitAll()
                                .anyRequest().authenticated());

          http.csrf().ignoringAntMatchers("/h2-console/**");
          http.headers().frameOptions().sameOrigin();
            http
                .formLogin()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
