package com.harsha.address.config;

import com.harsha.address.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity(debug = true)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    @Autowired
    CustomerServiceImpl customerServiceImpl;
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Autowired
    JWTAuthenticationFilter authenticationFilter;

    public static final String[] ENDPOINTS_WHITELIST = {
            "/css/**",
            "/",
            "/login",
            "/home",
            "/test",
            "/signup",
            "/h2-console/**"
    };

    @Bean
    public JWTAuthenticationFilter authenticationJwtTokenFilter() {
        return new JWTAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.
                authorizeRequests().antMatchers(ENDPOINTS_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll().and().httpBasic().and().
                cors().disable().csrf().disable().
                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().headers().frameOptions().sameOrigin().frameOptions().and().and()
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() throws Exception {

        DaoAuthenticationProvider authenticationProvide = new DaoAuthenticationProvider();
        authenticationProvide.setUserDetailsService(customerServiceImpl);
        authenticationProvide.setPasswordEncoder(passwordEncoder());

        return authenticationProvide;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
