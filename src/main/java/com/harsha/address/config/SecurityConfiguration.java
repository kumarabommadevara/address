package com.harsha.address.config;

import com.harsha.address.config.JWTAuthenticationFilter;
import com.harsha.address.config.RestAuthenticationEntryPoint;
import com.harsha.address.service.CustomerService;
import com.harsha.address.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity(debug = true)
@Configuration
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

        http.
                authorizeRequests().antMatchers(ENDPOINTS_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login.html").permitAll().and()
                .logout().permitAll().and().httpBasic();
        http.cors().disable().csrf().disable();

        http.headers().frameOptions().sameOrigin();
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and();
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(AuthenticationManagerBuilder auth) throws Exception {

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
