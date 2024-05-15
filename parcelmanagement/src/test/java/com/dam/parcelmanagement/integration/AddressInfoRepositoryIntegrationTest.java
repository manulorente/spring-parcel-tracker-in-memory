package com.dam.parcelmanagement.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dam.parcelmanagement.model.Address;
import com.dam.parcelmanagement.repository.AddressRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class AddressInfoRepositoryIntegrationTest {

	@Autowired
	private AddressRepository addressRepository;

	@Test
    public void testSave() {

        Address addressInfo = new Address();
        addressInfo.setEmail("test@test.com");
        addressInfo.setFirstName("John");
        addressInfo.setLastName("Doe");
        addressInfo.setStreet("123 Main St");
        addressInfo.setCity("Springfield");
        addressInfo.setZipCode("12345");
        addressInfo.setCountry("USA");

        Address savedAddressInfo = this.addressRepository.save(addressInfo);

        Address retrievedAddressInfo = addressRepository.findById(savedAddressInfo.getId()).orElse(null);
        assertNotNull(retrievedAddressInfo);

        assertEquals(savedAddressInfo.getEmail(), retrievedAddressInfo.getEmail());
        assertEquals(savedAddressInfo.getFirstName(), retrievedAddressInfo.getFirstName());
        assertEquals(savedAddressInfo.getLastName(), retrievedAddressInfo.getLastName());
        assertEquals(savedAddressInfo.getStreet(), retrievedAddressInfo.getStreet());
        assertEquals(savedAddressInfo.getCity(), retrievedAddressInfo.getCity());
        assertEquals(savedAddressInfo.getZipCode(), retrievedAddressInfo.getZipCode());
        assertEquals(savedAddressInfo.getCountry(), retrievedAddressInfo.getCountry());
    }

	@Test
	public void testFindById() {
		Address addressInfo = new Address();

		Address savedAddressInfo = this.addressRepository.save(addressInfo);
		Address retrievedAddressInfo = addressRepository.findById(savedAddressInfo.getId()).orElse(null);

		assertNotNull(retrievedAddressInfo);
		assertEquals(savedAddressInfo, retrievedAddressInfo);

	}

	@Test
	public void testDelete() {
		Address addressInfo = new Address();

		Address savedAddressInfo = this.addressRepository.save(addressInfo);
		this.addressRepository.deleteById(savedAddressInfo.getId());

		Address retrievedAddressInfo = addressRepository.findById(savedAddressInfo.getId()).orElse(null);
		assertEquals(null, retrievedAddressInfo);
	}

}
