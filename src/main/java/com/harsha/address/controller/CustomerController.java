package com.harsha.address.controller;

import com.harsha.address.model.SignupRequest;
import com.harsha.address.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/signup")
    public String signUp(@RequestBody SignupRequest signupRequest) {
        boolean userExits = customerService.checkIfUserExits(signupRequest.getName());
          if(userExits)
          {
              throw new RuntimeException("User already exsits with name"+ signupRequest.getName());
          }
          else {
              customerService.saveCustomer(signupRequest);
          }
          return "Sign Up Successfull";
    }
}
