package com.demo.api.helper;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL Injection class helper that contains a vulnerable insecureDBRequest function to
 * trigger a finding
 */
public class SQLInjectionHelper {
    private static Connection conn;

    /**
     * insecureDBRequest function that is vulnerable to SQL injection attacks
     * @param username
     * @return
     * @throws SQLException
     */
    public static ResultSet insecureDBRequest(String username) throws SQLException {
        if (username.isEmpty()) {
            return null;
        }

        // Using String.format instead of passing parameters through a PreparedStatement
        // will lead to a SQL injection
        String query = String.format("SELECT * FROM users WHERE username='%s'", username);
        return conn.createStatement().executeQuery(query);
    }

    public static void connect() throws SQLException{
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:database.db");
        conn = ds.getConnection();

        // A dummy database is dynamically created
        conn.createStatement().execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY PRIMARY KEY, username VARCHAR(50), name VARCHAR(50), password VARCHAR(50))");
        conn.createStatement().execute("INSERT INTO users (username, name, password) VALUES ('admin', 'Administrator', 'passw0rd')");
        conn.createStatement().execute("INSERT INTO users (username, name, password) VALUES ('john', ' John', 'hello123')");
    }
}
