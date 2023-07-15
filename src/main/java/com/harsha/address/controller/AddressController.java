package com.harsha.address.controller;

import com.harsha.address.model.Address;
import com.harsha.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "/addresses", method = RequestMethod.GET)
    public ResponseEntity<List<Address>> findAllAddresses() {
        List<Address> addresses = addressService.findAllAddresses();
        return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK);
    }
    @RequestMapping(value = "/save/address",method = RequestMethod.POST)
    public ResponseEntity<Address> saveAddress(@RequestBody Address address) {
         Address  savedAddress = addressService.saveAddress(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.OK);
    }
    @RequestMapping(value = "/address/{id}",method = RequestMethod.GET)
    public ResponseEntity<Address> findAddressById(@PathVariable("id") Integer id) {
        Address  address = addressService.findAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
    @RequestMapping(value = "/instructor/{id}",method = RequestMethod.GET)
    public ResponseEntity<Address> findAddressByInstructorId(@PathVariable("id") Integer instructorId) {
        Address  address = addressService.findAddressByInstructorId(instructorId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
}
