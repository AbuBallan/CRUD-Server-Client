package com.atypon.crud.server.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * * Connection Pool for database connection cache. main purpose is to reduce the overhead involved
 * in performing database connections and read/write database operations.
 */
public interface ConnectionPool {

  /**
   * *
   *
   * @return the connection fetched from the pool, so there's no need to create new ones.
   * @throws SQLException If the Driver fails to connect to the database.
   */
  Connection getConnection() throws SQLException;

  /**
   * * release a connection from used connection pool.
   *
   * @param connection the connection to release.
   * @return true if the release succeeded, false otherwise.
   */
  boolean releaseConnection(Connection connection);

  /**
   * The number of connections in the connection pool.
   *
   * @return size of the connection pool.
   */
  int getSize();

  /** Close connections in the connection pool. */
  void shutdown() throws SQLException;
}
