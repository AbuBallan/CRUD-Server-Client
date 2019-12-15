package com.atypon.crud.server.service;

import com.atypon.crud.server.exception.IllegalRequestException;
import com.atypon.crud.server.model.Employee;
import com.atypon.crud.server.repo.Repository;
import com.atypon.crud.server.requests.EntityRequest;
import com.atypon.crud.server.requests.Request;

/**
 * An implementation for Query for EmployeeInsert. for more detailed documentation: {@see Query}.
 */
public class EmployeeInsertQuery implements Query<Boolean> {

  private Repository<Employee> employeeRepository;

  public EmployeeInsertQuery(Repository<Employee> employeeRepository, Employee... employees) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Boolean execute(Request request) {
    if (!(request instanceof EntityRequest)) throw new IllegalRequestException();
    EntityRequest<Employee> entityRequest = (EntityRequest<Employee>) request;
    return employeeRepository.insert(entityRequest.getItem());
  }
}
