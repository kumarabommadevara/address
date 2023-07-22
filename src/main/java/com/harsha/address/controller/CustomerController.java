package com.harsha.address.controller;

import com.harsha.address.config.JwtUtils;
import com.harsha.address.model.Customer;
import com.harsha.address.model.JwtResponse;
import com.harsha.address.model.SigninRequest;
import com.harsha.address.model.SignupRequest;
import com.harsha.address.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/signup")
    public String signUp(@RequestBody SignupRequest signupRequest) {
        boolean userExits = customerService.checkIfUserExits(signupRequest.getName());
        if (userExits) {
            throw new RuntimeException("User already exsits with name" + signupRequest.getName());
        } else {
            customerService.saveCustomer(signupRequest);
        }
        return "Sign Up Successfull";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SigninRequest signinRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (signinRequest.getUsername(), signinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        //Customer userDetails = (Customer) authentication.getPrincipal();


        return ResponseEntity.ok(new JwtResponse(jwt,

                authentication.getName(),
            null

,            null));
    }

}
