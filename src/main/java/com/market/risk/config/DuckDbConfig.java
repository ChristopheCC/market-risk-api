package com.market.risk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DuckDbConfig {

    private static final String DB_URL = "jdbc:duckdb:./market_risk.duckdb";

    @Bean
    public Connection duckDbConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
