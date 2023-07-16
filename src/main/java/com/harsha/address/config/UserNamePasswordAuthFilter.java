package com.harsha.address.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsha.address.model.SigninRequest;
import com.harsha.address.repository.CustomerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserNamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final CustomerRepository customerRepository;

    public UserNamePasswordAuthFilter(AuthenticationManager authManager, CustomerRepository customerRepository) {
        super();
        this.authManager = authManager;
        this.customerRepository = customerRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            // Get username & password from request (JSON) any way you like
            SigninRequest authRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), SigninRequest.class);

            Authentication auth = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword());

            return authManager.authenticate(auth);
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

        @Override
        protected void successfulAuthentication (HttpServletRequest request,
                                                 HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

            if (logger.isDebugEnabled()) {
                logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
                        + authResult);
            }

            // custom code

            SecurityContextHolder.getContext().setAuthentication(authResult);
        }
    }