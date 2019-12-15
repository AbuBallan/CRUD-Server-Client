package com.atypon.crud.client.data.db;

import com.atypon.crud.client.io.socket.SocketReader;
import com.atypon.crud.client.io.socket.SocketWriter;
import com.atypon.crud.client.io.socket.StringSocketReader;
import com.atypon.crud.client.io.socket.StringSocketWriter;
import com.atypon.crud.client.model.Employee;
import com.atypon.crud.client.requests.ByIdRequest;
import com.atypon.crud.client.requests.EntityRequest;
import com.atypon.crud.client.requests.Request;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.atypon.crud.client.utils.AppConstants.HOST;
import static com.atypon.crud.client.utils.AppConstants.PORT;

/** An implementation for DBHelper, for more detailed documentation: {@see DBHelper}. */
public class AppDBHelper implements DBHelper<Employee> {

  private final Gson gson;

  private static AppDBHelper INSTANCE;

  private AppDBHelper(Gson gson) {
    this.gson = gson;
  }

  public static AppDBHelper getInstance(Gson gson) {
    if (INSTANCE == null) {
      INSTANCE = new AppDBHelper(gson);
    }
    return INSTANCE;
  }

  @Override
  public boolean insert(EntityRequest<Employee> request) {
    String response = getJsonResponseFromServer(request);
    return fromJson(response, Boolean.class);
  }

  @Override
  public boolean updateById(ByIdRequest request) {
    String response = getJsonResponseFromServer(request);
    return fromJson(response, Boolean.class);
  }

  @Override
  public List<Employee> findAll(Request request) {
    String response = getJsonResponseFromServer(request);
    Type listType = new TypeToken<ArrayList<Employee>>() {}.getType();
    return fromJson(response, listType);
  }

  @Override
  public Optional<Employee> findById(ByIdRequest request) {
    String response = getJsonResponseFromServer(request);
    try {
      return Optional.of(fromJson(response, Employee.class));
    } catch (JsonParseException e) {
      return Optional.empty();
    }
  }

  @Override
  public boolean deleteById(ByIdRequest request) {
    String response = getJsonResponseFromServer(request);
    return fromJson(response, Boolean.class);
  }

  private String getJsonResponseFromServer(Request request) {
    validateRequest(request);
    String jsonRequest = toJson(request);
    try (Socket socket = new Socket(HOST, PORT);
        SocketWriter<String> writer = new StringSocketWriter(socket);
        SocketReader<String> reader = new StringSocketReader(socket); ) {
      writer.write(jsonRequest);
      return reader.readValue();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void validateRequest(Request request) {
    if (request == null) throw new IllegalArgumentException();
  }

  private String toJson(Request request) {
    return gson.toJson(request);
  }

  private <U> U fromJson(String json, Class<U> uClass) {
    return gson.fromJson(json, uClass);
  }

  private <U> U fromJson(String json, Type type) {
    return gson.fromJson(json, type);
  }
}
