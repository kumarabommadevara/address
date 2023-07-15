package com.harsha.address.service;

import com.harsha.address.model.Address;
import com.harsha.address.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> findAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            throw new RuntimeException("No addresses present");
        }
        return addresses;
    }

    public Address findAddressByInstructorId(Integer instructorId) {
        return addressRepository.findAddressByInstructorId(instructorId).orElseThrow(() ->
                new RuntimeException("Address for the instructor id " + instructorId + "is not present"));
    }

    public Address findAddressById(Integer id) {
        return addressRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Address for the id " + id + "is not present"));
    }

}
