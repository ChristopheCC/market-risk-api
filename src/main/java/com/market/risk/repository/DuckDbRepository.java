package com.market.risk.repository;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DuckDbRepository {

    private final Connection connection;

    public DuckDbRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Map<String, Object>> query(String sql) {
        List<Map<String, Object>> result = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                java.util.Map<String, Object> row = new java.util.HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                result.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL: " + sql, e);
        }
        return result;
    }
}
