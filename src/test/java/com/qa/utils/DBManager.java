package com.qa.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DBManager {
    private static Connection conn;

    public static void initialize() throws Exception {
        if (conn == null || conn.isClosed()) {
            try {
                Properties props = new PropertyManager().getProps();

                String url = System.getenv("db.url") != null ? System.getenv("db.url") : props.getProperty("db.url");
                String user = System.getenv("db.user") != null ? System.getenv("db.user") : props.getProperty("db.user");
                String password = System.getenv("db.password") != null ? System.getenv("db.password") : props.getProperty("db.password");

                TestUtils.log().info("Connecting to Database: " + url);
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
                TestUtils.log().info("Database Connected Successfully!");
            } catch (Exception e) {
                TestUtils.log().error("DATABASE CONNECTION FAILED: " + e.getMessage());
                throw e;
            }
        }
    }

    public static Connection getConnection() throws Exception {
        if (conn == null || conn.isClosed()) {
            initialize();
        }
        return conn;
    }

    public static void close() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            TestUtils.log().info("Database Connection Closed.");
        }
    }

    public static ResultSet executeQuery(String query) throws Exception {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery(query);
    }

    public static ResultSet getUserByUsername(String username) throws Exception {
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setString(1, username);
        return pstmt.executeQuery();
    }

    public static ResultSet getAddressByStreet(String street) throws Exception {
        String query = "SELECT * FROM addresses WHERE street = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setString(1, street);
        return pstmt.executeQuery();
    }

    public static ResultSet getContactByName(String name) throws Exception {
        String query = "SELECT * FROM contacts WHERE name = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setString(1, name);
        return pstmt.executeQuery();
    }
}
