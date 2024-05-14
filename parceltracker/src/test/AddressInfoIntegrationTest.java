
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dam.model.AddressInfo;
import com.dam.repository.AddressInfoRepository;

@SpringBootTest
public class AddressInfoIntegrationTest {

    @Autowired
    private AddressInfoRepository addressInfoRepository;

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
}
