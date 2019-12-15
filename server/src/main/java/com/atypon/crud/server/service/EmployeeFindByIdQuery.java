package com.atypon.crud.server.service;

import com.atypon.crud.server.exception.IllegalRequestException;
import com.atypon.crud.server.model.Employee;
import com.atypon.crud.server.repo.Repository;
import com.atypon.crud.server.requests.ByIdRequest;
import com.atypon.crud.server.requests.Request;

import java.util.Optional;

/**
 * An implementation for Query for EmployeeFindById. for more detailed documentation: {@see Query}.
 */
public class EmployeeFindByIdQuery implements Query<Optional<Employee>> {

  private Repository<Employee> employeeRepository;

  public EmployeeFindByIdQuery(Repository<Employee> employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Optional<Employee> execute(Request request) {
    if (!(request instanceof ByIdRequest)) throw new IllegalRequestException();
    ByIdRequest byIdRequest = (ByIdRequest) request;
    return employeeRepository.findById(byIdRequest.getId());
  }
}
