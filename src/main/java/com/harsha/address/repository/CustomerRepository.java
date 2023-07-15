package com.harsha.address.repository;

import com.harsha.address.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "Select * from Customers c where c.name=?",nativeQuery = true)
    Customer findByUserName(String username);
}
