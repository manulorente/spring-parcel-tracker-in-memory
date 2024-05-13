package com.dam.unitary;

import com.dam.model.Customer;
import com.dam.model.Roles;
import com.dam.model.UserInfo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomerUnitTest {

    @Test
    public void testDefaultConstructor() {
        Customer customer = new Customer();
        assertNotNull(customer);
        assertEquals(Roles.ROLE_CUSTOMER, customer.getRol());
        assertNull(customer.getUsername());
        assertNull(customer.getPassword());
        assertNull(customer.getUserInfo());
        assertNull(customer.getPackets());
    }

    @Test
    public void testParameterizedConstructor() {
        UserInfo userInfo = new UserInfo("test@example.com", "John", "Doe", "123 Main St", "City", "12345", "Country");
        Customer customer = new Customer("customer", "password", userInfo);

        assertNotNull(customer);
        assertEquals("customer", customer.getUsername());
        assertEquals("password", customer.getPassword());
        assertEquals(Roles.ROLE_CUSTOMER, customer.getRol());
        assertEquals(userInfo, customer.getUserInfo());
        assertNull(customer.getPackets());
    }

    @Test
    public void testParckets() {
        assert(true);
    }
}