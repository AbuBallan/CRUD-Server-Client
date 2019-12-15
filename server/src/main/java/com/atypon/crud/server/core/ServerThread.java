package com.atypon.crud.server.core;

import com.atypon.crud.server.cache.EmployeeCache;
import com.atypon.crud.server.cache.ICache;
import com.atypon.crud.server.connectionpool.BasicConnectionPool;
import com.atypon.crud.server.connectionpool.ConnectionPool;
import com.atypon.crud.server.model.Employee;
import com.atypon.crud.server.repo.mysql.EmployeeMySqlRepository;
import com.atypon.crud.server.utils.DatabaseUtils;
import com.google.gson.Gson;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.atypon.crud.server.utils.AppConstants.SERVER_PORT;

/**
 * ServerThread class which contains the main functionality of the server. The class extends Thread
 * as it runs independently from the interface thread from Main class.
 */
public class ServerThread extends Thread {

  // A boolean to check if the server is running or not.
  private boolean isRunning;

  // A ExecutorService contains ClientRunnable Threads
  private ExecutorService mExecutor;

  private ICache<Employee> cache;

  private ConnectionPool connectionPool;

  /** constructor to initialize a server. */
  public ServerThread() {
    cache = EmployeeCache.getInstance();
    try {
      connectionPool = BasicConnectionPool.getInstance();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /** The method called when the server starts as a separate thread. */
  @Override
  public void run() {
    DatabaseUtils.loadJDBCDriver();
    runServerSocket();
  }

  /** Listens to a connection, once listened, it answers the connection in Client thread. */
  private void runServerSocket() {

    mExecutor = Executors.newCachedThreadPool();

    this.isRunning = true;
    try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
      while (isRunning) {
        Socket socket = serverSocket.accept();
        ClientRunnable clientRunnable =
            new ClientRunnable(socket, cache, connectionPool, this::gsonInitial, this::repoInitial);
        mExecutor.submit(clientRunnable);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    shutdown();
  }

  private void shutdown() {
    mExecutor.shutdown();

    try {
      connectionPool.shutdown();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private EmployeeMySqlRepository repoInitial() {
    return new EmployeeMySqlRepository();
  }

  private Gson gsonInitial() {
    return new Gson();
  }

  /**
   * Stops the server from running by setting the isRunning flag used by the while loop to false and
   * also closing the ServerSocket which interrupts the listening for a new connection thus breaking
   * loop.
   */
  public void stopServer() {
    if (!isRunning) return;
    isRunning = false;
  }
}
