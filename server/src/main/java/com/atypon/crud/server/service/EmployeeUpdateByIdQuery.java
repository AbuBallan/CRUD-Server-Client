package com.atypon.crud.server.service;

import com.atypon.crud.server.exception.IllegalRequestException;
import com.atypon.crud.server.model.Employee;
import com.atypon.crud.server.repo.Repository;
import com.atypon.crud.server.requests.EntityByIdRequest;
import com.atypon.crud.server.requests.Request;

/**
 * An implementation for Query for EmployeeUpdateById. for more detailed documentation: {@see
 * Query}.
 */
public class EmployeeUpdateByIdQuery implements Query<Boolean> {

  private Repository<Employee> employeeRepository;

  public EmployeeUpdateByIdQuery(Repository<Employee> employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Boolean execute(Request request) {
    if (!(request instanceof EntityByIdRequest)) throw new IllegalRequestException();
    EntityByIdRequest<Employee> entityByIdRequest = (EntityByIdRequest<Employee>) request;
    return employeeRepository.updateById(entityByIdRequest.getId(), entityByIdRequest.getItem());
  }
}
