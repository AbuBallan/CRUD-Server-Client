package com.atypon.crud.server.connectionpool;

import com.atypon.crud.server.cache.EmployeeCache;
import com.atypon.crud.server.exception.ConnectionOverflowException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.atypon.crud.server.utils.DatabaseUtils.createConnection;

/** An implementation for ConnectionPool for more detailed documentation: {@see ConnectionPool}. */
public class BasicConnectionPool implements ConnectionPool {

  private static final int INITIAL_POOL_SIZE = 6;

  private final int MAX_POOL_SIZE = 40;

  private static volatile BasicConnectionPool INSTANCE;

  // Uses BlockingQueue to avoid concurrency issues if multi-threading is used.
  private final Queue<Connection> connectionPool;

  // Uses BlockingQueue to avoid concurrency issues if multi-threading is used.
  private final Queue<Connection> usedConnections;

  private BasicConnectionPool() throws SQLException {
    connectionPool = new LinkedBlockingQueue<>();
    usedConnections = new LinkedBlockingQueue<>();
    for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
      connectionPool.add(createConnection());
    }
  }

  public static BasicConnectionPool getInstance() throws SQLException {
    if (INSTANCE == null) {
      synchronized (EmployeeCache.class) {
        if (INSTANCE == null) INSTANCE = new BasicConnectionPool();
      }
    }
    return INSTANCE;
  }

  /**
   * Creates a connection with the database if the limit {@link #MAX_POOL_SIZE} isn't reached,
   * otherwise gets a connection from the connection pool.
   *
   * @throws ConnectionOverflowException if the limit {@link #MAX_POOL_SIZE} is reached
   */
  @Override
  public Connection getConnection() throws SQLException {

    if (connectionPool.isEmpty()) {
      if (usedConnections.size() < MAX_POOL_SIZE) {
        Connection newConnection = createConnection();
        connectionPool.add(newConnection);
      } else {
        throw new ConnectionOverflowException();
      }
    }

    Connection connection = connectionPool.remove();
    usedConnections.add(connection);
    return connection;
  }

  @Override
  public boolean releaseConnection(Connection connection) {
    connectionPool.add(connection);
    return usedConnections.remove(connection);
  }

  @Override
  public int getSize() {
    return connectionPool.size() + usedConnections.size();
  }

  @Override
  public void shutdown() throws SQLException {
    usedConnections.forEach(this::releaseConnection);
    for (Connection c : connectionPool) {
      c.close();
    }
    connectionPool.clear();
  }
}
