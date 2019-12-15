package com.atypon.crud.client.data;

import com.atypon.crud.client.data.db.DBHelper;
import com.atypon.crud.client.model.Employee;
import com.atypon.crud.client.requests.ByIdRequest;
import com.atypon.crud.client.requests.EntityRequest;
import com.atypon.crud.client.requests.Request;

import java.util.List;
import java.util.Optional;

/** An implementation for DataManager, for more detailed documentation: {@see DataManager}. */
public class AppDataManager implements DataManager {

  private static AppDataManager INSTANCE;

  private final DBHelper<Employee> dbHelper;

  private AppDataManager(DBHelper<Employee> dbHelper) {
    this.dbHelper = dbHelper;
  }

  public static AppDataManager getInstance(DBHelper<Employee> dbHelper) {
    if (INSTANCE == null) {
      INSTANCE = new AppDataManager(dbHelper);
    }
    return INSTANCE;
  }

  @Override
  public boolean insert(EntityRequest<Employee> request) {
    return dbHelper.insert(request);
  }

  @Override
  public boolean updateById(ByIdRequest request) {
    return dbHelper.updateById(request);
  }

  @Override
  public List<Employee> findAll(Request request) {
    return dbHelper.findAll(request);
  }

  @Override
  public Optional<Employee> findById(ByIdRequest request) {
    return dbHelper.findById(request);
  }

  @Override
  public boolean deleteById(ByIdRequest request) {
    return dbHelper.deleteById(request);
  }
}
