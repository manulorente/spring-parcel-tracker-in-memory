package com.dam.parcelmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@AutoConfigureTestDatabase
public class DataSqlTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void  testAddressDataSqlExecution() {
        assert jdbcTemplate.queryForObject("SELECT COUNT(*) FROM addresses", Integer.class) > 1;
    }

    @Test
    public void  testInvoiceDataSqlExecution() {
        assert jdbcTemplate.queryForObject("SELECT COUNT(*) FROM  invoices", Integer.class) > 1;
    }

    @Test
    public void  testUserSqlExecution() {
        assert jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class) > 1;
    }

    @Test
    public void  testDeliveryDataSqlExecution() {
        assert jdbcTemplate.queryForObject("SELECT COUNT(*) FROM deliveries", Integer.class) > 1;
    }

    @Test
    public void  testCommentDataSqlExecution() {
        assert jdbcTemplate.queryForObject("SELECT COUNT(*) FROM comments", Integer.class) > 1;
    }

    @Test
    public void  testReportDataSqlExecution() {
        assert jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reports", Integer.class) > 1;
    }
}
