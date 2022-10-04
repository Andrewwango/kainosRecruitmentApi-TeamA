package com.kainos.ea.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
  public Connection createConnection(String jdbcUrl, Properties props) throws SQLException {
    return DriverManager.getConnection(jdbcUrl, props);
  }
}