package com.atypon.crud.client.data;

import com.atypon.crud.client.data.db.DBHelper;
import com.atypon.crud.client.model.Employee;

/** * DataManager interface that manage the whole data on the app */
public interface DataManager extends DBHelper<Employee> {}
