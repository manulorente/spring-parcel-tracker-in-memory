package com.dam.parcelmanagement.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@AutoConfigureTestDatabase
public class DataSqlIntegrationTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void  testDataSqlExecution() {
        assert jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class) == 2;
        assert jdbcTemplate.queryForObject("SELECT COUNT(*) FROM address", Integer.class) == 2;
    }
}
