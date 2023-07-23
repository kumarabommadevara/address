package com.harsha.address.service;

import com.harsha.address.model.Customer;
import com.harsha.address.model.SignupRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean checkIfUserExits(String name);

    void saveCustomer(SignupRequest signupRequest);
}
