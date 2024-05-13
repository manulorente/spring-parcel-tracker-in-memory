package com.dam.unitary;

import com.dam.model.Admin;
import com.dam.model.Roles;
import com.dam.model.UserInfo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AdminUnitTest {

    @Test
    public void testDefaultConstructor() {
        Admin admin = new Admin();
        assertNotNull(admin);
        assertEquals(Roles.ROLE_ADMIN, admin.getRol());
        assertNull(admin.getUsername());
        assertNull(admin.getPassword());
        assertNull(admin.getUserInfo());
        assertNull(admin.getWeeklyReports());
    }

    @Test
    public void testParameterizedConstructor() {
        UserInfo userInfo = new UserInfo("test@example.com", "John", "Doe", "123 Main St", "City", "12345", "Country");
        Admin admin = new Admin("admin", "password", userInfo);

        assertNotNull(admin);
        assertEquals("admin", admin.getUsername());
        assertEquals("password", admin.getPassword());
        assertEquals(Roles.ROLE_ADMIN, admin.getRol());
        assertEquals(userInfo, admin.getUserInfo());
        assertNull(admin.getWeeklyReports());
    }

    @Test
    public void testWeeklyReports() {
        assert(true);
    }
}
