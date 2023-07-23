package com.harsha.address.controller;

import com.harsha.address.model.Address;
import com.harsha.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;

@RestController
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "/addresses", method = RequestMethod.GET)

   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Address>> findAllAddresses() {
        List<Address> addresses = addressService.findAllAddresses();
        return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/save/address",method = RequestMethod.POST)
    public ResponseEntity<Address> saveAddress(@RequestBody Address address) {
         Address  savedAddress = addressService.saveAddress(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value = "/address/{id}",method = RequestMethod.GET)
    public ResponseEntity<Address> findAddressById(@PathVariable("id") Integer id) {
        Address  address = addressService.findAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
    @RequestMapping(value = "/instructor/{id}",method = RequestMethod.GET)
    public ResponseEntity<Address> findAddressByInstructorId( @RequestHeader(name ="application-name")
                                                                 String applicationName,
                                                             @PathVariable("id") Integer instructorId) {
        System.out.println("application-name"+applicationName);
        if(applicationName==null)
        {
            throw new RuntimeException("application-name cannot be empty");
        }
        Address  address = addressService.findAddressByInstructorId(instructorId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
}
