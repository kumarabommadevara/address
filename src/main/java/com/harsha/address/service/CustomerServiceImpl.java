package com.harsha.address.service;

import com.harsha.address.model.Customer;
import com.harsha.address.repository.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByUserName(username);

        if(customer==null)
        {
            throw  new RuntimeException("customer with name "+username +"not found");
        }

      return User.withUsername(customer.getName())
                .password(customer.getPassword())
              .roles(customer.getCrole())
                .build();

    }
}
