package com.dam.parcelmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.exception.ResourceNotFoundException;
import com.dam.parcelmanagement.model.Address;
import com.dam.parcelmanagement.repository.AddressRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Boolean existsById(Long id) {
        return this.addressRepository.existsById(id);
    }

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

    @Transactional
    public Address createAddress(Address address) {
        if (address.getId() != null && this.addressRepository.existsById(address.getId())) {
            throw new ResourceNotFoundException("Address already exists with id: " + address.getId());
        }
        return this.addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(Long id, Address addressDetails) {
        Optional<Address> address = this.addressRepository.findById(id);
        if (address.isPresent()) {
            Address addressToUpdate = address.get();
            if (addressDetails.getEmail() != null) {
                addressToUpdate.setEmail(addressDetails.getEmail());
            }
            if (addressDetails.getFirstName() != null) {
                addressToUpdate.setFirstName(addressDetails.getFirstName());
            }
            if (addressDetails.getLastName() != null) {
                addressToUpdate.setLastName(addressDetails.getLastName());
            }
            if (addressDetails.getStreet() != null) {
                addressToUpdate.setStreet(addressDetails.getStreet());
            }
            if (addressDetails.getCity() != null) {
                addressToUpdate.setCity(addressDetails.getCity());
            }
            if (addressDetails.getZipCode() != null) {
                addressToUpdate.setZipCode(addressDetails.getZipCode());
            }
            if (addressDetails.getCountry() != null) {
                addressToUpdate.setCountry(addressDetails.getCountry());
            }
            return this.addressRepository.save(addressToUpdate);
        } else {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
    }

    @Transactional
    public void deleteAddress(Long id) {
        if (!this.addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        this.addressRepository.deleteById(id);
    }
    
}
