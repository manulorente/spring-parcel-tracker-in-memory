package com.dam.parcelmanagement.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dam.parcelmanagement.model.AddressInfo;
import com.dam.parcelmanagement.repository.AddressInfoRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class AddressInfoRepositoryIntegrationTest {

	@Autowired
	private AddressInfoRepository addressInfoRepository;

	@BeforeEach
	public void setUp() {
		this.addressInfoRepository.deleteAll();
	}

	@Test
	@Transactional
    public void testSave() {

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setEmail("test@test.com");
        addressInfo.setFirstName("John");
        addressInfo.setLastName("Doe");
        addressInfo.setStreet("123 Main St");
        addressInfo.setCity("Springfield");
        addressInfo.setZipCode("12345");
        addressInfo.setCountry("USA");

        AddressInfo savedAddressInfo = this.addressInfoRepository.save(addressInfo);

        AddressInfo retrievedAddressInfo = addressInfoRepository.findById(savedAddressInfo.getId()).orElse(null);
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
	@Transactional
	public void testFindById() {
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setId(1L);

		AddressInfo savedAddressInfo = this.addressInfoRepository.save(addressInfo);
		AddressInfo retrievedAddressInfo = addressInfoRepository.findById(savedAddressInfo.getId()).orElse(null);

		assertNotNull(retrievedAddressInfo);
		assertEquals(savedAddressInfo.getId(), retrievedAddressInfo.getId());

	}

	@Test
	@Transactional
	public void testDelete() {
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setId(1L);

		AddressInfo savedAddressInfo = this.addressInfoRepository.save(addressInfo);
		this.addressInfoRepository.deleteById(savedAddressInfo.getId());

		AddressInfo retrievedAddressInfo = addressInfoRepository.findById(savedAddressInfo.getId()).orElse(null);
		assertEquals(null, retrievedAddressInfo);
	}

}
