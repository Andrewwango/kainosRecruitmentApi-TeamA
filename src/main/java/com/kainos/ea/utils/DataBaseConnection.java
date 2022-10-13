package com.kainos.ea.utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DataBaseConnection {

    private static Connection conn;

    public Connection getConnection() {
        String user;
        String password;
        String host;
        String database;

        if (conn != null) {
            return conn;
        }

        try {

            user            = System.getenv("DB_USERNAME");
            password        = System.getenv("DB_PASSWORD");
            host            = System.getenv("DB_HOST");

            if (user == null || password == null || host == null)
                throw new IllegalArgumentException(
                        "Properties file must exist and must contain "
                                + "user, password, and host Properties.");

            ConnectionFactory connectionFactory = new ConnectionFactory();

            conn = DriverManager.getConnection("jdbc:mysql://"
                    + host + ":" + "3306" + "/team_A?allowPublicKeyRetrieval=true&useSSL=false", user,password);

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
