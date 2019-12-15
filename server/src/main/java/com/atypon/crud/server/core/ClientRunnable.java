package com.atypon.crud.server.core;

import com.atypon.crud.server.cache.ICache;
import com.atypon.crud.server.connectionpool.ConnectionPool;
import com.atypon.crud.server.exception.IllegalRequestException;
import com.atypon.crud.server.exception.NotFoundRequestException;
import com.atypon.crud.server.io.socket.SocketReader;
import com.atypon.crud.server.io.socket.SocketWriter;
import com.atypon.crud.server.io.socket.StringSocketReader;
import com.atypon.crud.server.io.socket.StringSocketWriter;
import com.atypon.crud.server.model.Employee;
import com.atypon.crud.server.repo.mysql.EmployeeMySqlRepository;
import com.atypon.crud.server.requests.ByIdRequest;
import com.atypon.crud.server.requests.EntityByIdRequest;
import com.atypon.crud.server.requests.EntityRequest;
import com.atypon.crud.server.requests.Request;
import com.atypon.crud.server.service.*;
import com.atypon.crud.server.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Supplier;

import static com.atypon.crud.server.utils.AppConstants.RequestType.FIND_BY_ID_EMPLOYEE;

/** A class that handles a single connection with a client */
public class ClientRunnable implements Runnable {

  private Socket socket;

  private ThreadLocal<Gson> gsonThreadLocal;

  private ThreadLocal<EmployeeMySqlRepository> repositoryThreadLocal;

  private ConnectionPool connectionPool;

  private ICache<Employee> cache;

  /**
   * Parameterized constructor to initialize the ServerThread.
   *
   * @param socket The server socket.
   * @param cache The Cache to use for the server.
   * @param connectionPool The ConnectionPool to use for the server.
   * @param gsonInitial The initial value of gsonThreadLoacl
   * @param repoInitial The initial value of repositoryThreadLocal
   */
  public ClientRunnable(
      Socket socket,
      ICache<Employee> cache,
      ConnectionPool connectionPool,
      Supplier<? extends Gson> gsonInitial,
      Supplier<? extends EmployeeMySqlRepository> repoInitial) {
    if (socket == null) throw new IllegalArgumentException();
    this.socket = socket;
    this.cache = cache;
    this.connectionPool = connectionPool;
    this.gsonThreadLocal = ThreadLocal.withInitial(gsonInitial);
    this.repositoryThreadLocal = ThreadLocal.withInitial(repoInitial);
  }

  @Override
  public void run() {
    try (SocketReader<String> reader = new StringSocketReader(socket);
        SocketWriter<String> writer = new StringSocketWriter(socket)) {

      String requestString = reader.readValue();
      System.out.printf("%s request : %s\n", Thread.currentThread().getName(), requestString);
      long startTime = System.currentTimeMillis();
      Request request = getRequest(requestString);
      String result;
      if (request.getRequestType() == FIND_BY_ID_EMPLOYEE) {
        result = getJsonResultFromCache(request);
        System.out.print("From Cache ");
      } else {
        result = getJsonResult(request);
      }
      System.out.printf(
          "%s result in %dms : %s\n",
          Thread.currentThread().getName(), System.currentTimeMillis() - startTime, result);
      writer.write(result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private String getJsonResultFromCache(Request request) {
    Optional<Employee> optionalEmployee = getResultFromCache(request);
    if (optionalEmployee.isPresent()) {
      Employee employee = optionalEmployee.get();
      return toJson(employee);
    } else {
      return toJson(false);
    }
  }

  private String getJsonResult(Request request) {
    Object result = getResult(request);
    return toJson(result);
  }

  private String toJson(Object o) {
    Gson gson = gsonThreadLocal.get();
    return gson.toJson(o);
  }

  private Optional<Employee> getResultFromCache(Request request) {
    if (!(request instanceof ByIdRequest)) throw new IllegalRequestException();
    ByIdRequest byIdRequest = (ByIdRequest) request;
    return cache.computeIfAbsent(
        byIdRequest.getId(),
        (id) -> {
          ByIdRequest idRequest = new ByIdRequest(FIND_BY_ID_EMPLOYEE, id);
          return (Optional<Employee>) getResult(idRequest);
        });
  }

  private Object getResult(Request request) {
    EmployeeMySqlRepository repository = repositoryThreadLocal.get();
    initRepository(repository);
    Object result = handleRequest(request, repository);
    releaseConnection(repository);
    return result;
  }

  private Object handleRequest(Request request, EmployeeMySqlRepository repository) {
    AppConstants.RequestType requestType = request.getRequestType();
    switch (requestType) {
      case FIND_ALL_EMPLOYEES:
        return new EmployeeFindAllQuery(repository).execute(request);
      case FIND_BY_ID_EMPLOYEE:
        return new EmployeeFindByIdQuery(repository).execute(request);
      case INSERT_EMPLOYEE:
        return new EmployeeInsertQuery(repository).execute(request);
      case UPDATE_BY_ID_EMPLOYEE:
        return new EmployeeUpdateByIdQuery(repository).execute(request);
      case DELETE_BY_ID_EMPLOYEE:
        return new EmployeeDeleteByIdQuery(repository).execute(request);
    }
    throw new NotFoundRequestException();
  }

  private void initRepository(EmployeeMySqlRepository repository) {
    try {
      Connection connection = connectionPool.getConnection();
      repository.setConnection(connection);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void releaseConnection(EmployeeMySqlRepository repository) {
    final Connection connection = repository.getConnection();
    connectionPool.releaseConnection(connection);
  }

  private Request getRequest(String requestString) {
    final Request request = gsonThreadLocal.get().fromJson(requestString, Request.class);
    switch (request.getRequestType()) {
      case FIND_ALL_EMPLOYEES:
        return request;
      case FIND_BY_ID_EMPLOYEE:
      case DELETE_BY_ID_EMPLOYEE:
        return gsonThreadLocal.get().fromJson(requestString, ByIdRequest.class);
      case UPDATE_BY_ID_EMPLOYEE:
        Type entityByIdType = new TypeToken<EntityByIdRequest<Employee>>() {}.getType();
        return gsonThreadLocal.get().fromJson(requestString, entityByIdType);
      case INSERT_EMPLOYEE:
        Type entityType = new TypeToken<EntityRequest<Employee>>() {}.getType();
        return gsonThreadLocal.get().fromJson(requestString, entityType);
    }
    throw new NotFoundRequestException();
  }
}
