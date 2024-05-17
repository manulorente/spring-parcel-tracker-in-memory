package com.dam.parcelmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.exception.ResourceNotFoundException;
import com.dam.parcelmanagement.model.Address;
import com.dam.parcelmanagement.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return this.addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        Optional<Address> address = this.addressRepository.findById(id);
        if (!address.isPresent()) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        return address.get();
    }

    public Address createAddress(Address address) {
        if (address.getId() != null && this.addressRepository.existsById(address.getId())) {
            throw new ResourceNotFoundException("Address already exists with id: " + address.getId());
        }
        return this.addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address addressDetails) {
        Optional<Address> address = this.addressRepository.findById(id);
        if (address.isPresent()) {
            Address addressToUpdate = address.get();
            addressToUpdate.setEmail(addressDetails.getEmail());
            addressToUpdate.setFirstName(addressDetails.getFirstName());
            addressToUpdate.setLastName(addressDetails.getLastName());
            addressToUpdate.setStreet(addressDetails.getStreet());
            addressToUpdate.setCity(addressDetails.getCity());
            addressToUpdate.setZipCode(addressDetails.getZipCode());
            addressToUpdate.setCountry(addressDetails.getCountry());
            return this.addressRepository.save(addressToUpdate);
        } else {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
    }

    public void deleteAddress(Long id) {
        if (!this.addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        this.addressRepository.deleteById(id);
    }
    
}
