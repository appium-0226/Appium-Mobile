package com.qa.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DBManager {
    private static Connection conn;

    public static void initialize() throws Exception {
        if (conn == null || conn.isClosed()) {
            try {
                Properties props = new PropertyManager().getProps();
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                System.out.println(">>> Connecting to Database: " + url);
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println(">>> Database Connected Successfully!");
            } catch (Exception e) {
                System.err.println(">>> DATABASE CONNECTION FAILED: " + e.getMessage());
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
            System.out.println(">>> Database Connection Closed.");
        }
    }

    public static ResultSet executeQuery(String query) throws Exception {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery(query);
    }
}
