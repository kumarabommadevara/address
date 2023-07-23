package com.harsha.address.service;

import com.harsha.address.model.CustomCustomerDetail;
import com.harsha.address.model.Customer;
import com.harsha.address.model.SignupRequest;
import com.harsha.address.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByUserName(username);

        if (customer == null) {
            throw new RuntimeException("customer with name " + username + "not found");
        }

     /*   return User.withUsername(customer.getUsername())
                .password(customer.getPassword())
                .roles(customer.getCrole())
                .build();*/
        Set<GrantedAuthority> grantedAuthorityList = new HashSet<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(customer.getCrole());
        grantedAuthorityList.add(grantedAuthority);


        CustomCustomerDetail customCustomerDetail = new CustomCustomerDetail();
        customCustomerDetail.setCustomer(customer);
        customCustomerDetail.setAuthorities(grantedAuthorityList);

        return customCustomerDetail;
    }

    @Override
    public boolean checkIfUserExits(String name) {
        Customer customer = customerRepository.findByUserName(name);
        if (customer == null) {
            return false;
        }
        return true;
    }

    @Override
    public void saveCustomer(SignupRequest signupRequest) {
        Customer customer = new Customer();
        customer.setUsername(signupRequest.getName());
        customer.setEmail(signupRequest.getEmail());
        customer.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        customerRepository.save(customer);
    }
}
