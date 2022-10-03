package com.kainos.ea.utils;

import java.io.FileInputStream;
import java.sql.Connection;

import java.util.Properties;

public class DataBaseConnection {

    private static Connection conn;

    public Connection getConnection() {
        String user;
        String password;
        String host;

        if (conn != null) {
            return conn;
        }

        try {
            FileInputStream propsStream =
                    new FileInputStream("src/main/resources/Properties");
            Properties props = new Properties();
            props.load(propsStream);

            user            = props.getProperty("user");
            password        = props.getProperty("password");
            host            = props.getProperty("host");

            if (user == null || password == null || host == null)
                throw new IllegalArgumentException(
                        "Properties file must exist and must contain "
                                + "user, password, and host properties.");

            ConnectionFactory connectionFactory = new ConnectionFactory();

            conn = connectionFactory.createConnection("jdbc:mysql://"
                    + host + ":" + "3306" + "/team_A",  props);

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
