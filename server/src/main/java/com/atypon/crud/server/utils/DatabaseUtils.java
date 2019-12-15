package com.atypon.crud.server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** A Utility class to simplify work with MySQL and handling */
public final class DatabaseUtils {

  public static final String DB_URL = "jdbc:mysql://localhost:3306/atypon";
  public static final String DB_USER = "root";
  public static final String DB_PASSWORD = "0000";

  /** * Load JDBC Drivers */
  public static void loadJDBCDriver() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates a connection with the database
   *
   * @return A connection.
   * @throws SQLException If the Driver fails to connect to the database.
   */
  public static Connection createConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
  }

  private DatabaseUtils() {}
}
