package com.atypon.crud.server.service;

import com.atypon.crud.server.exception.IllegalRequestException;
import com.atypon.crud.server.model.Employee;
import com.atypon.crud.server.repo.Repository;
import com.atypon.crud.server.requests.ByIdRequest;
import com.atypon.crud.server.requests.Request;

/**
 * An implementation for Query for EmployeeDeleteById. for more detailed documentation: {@see
 * Query}.
 */
public class EmployeeDeleteByIdQuery implements Query<Boolean> {

  private Repository<Employee> employeeRepository;

  public EmployeeDeleteByIdQuery(Repository<Employee> employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Boolean execute(Request request) {
    if (!(request instanceof ByIdRequest)) throw new IllegalRequestException();
    ByIdRequest byIdRequest = (ByIdRequest) request;
    return employeeRepository.deleteById(byIdRequest.getId());
  }
}
