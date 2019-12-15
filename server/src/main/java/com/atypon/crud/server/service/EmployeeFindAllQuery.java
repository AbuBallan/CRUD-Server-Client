package com.atypon.crud.server.service;

import com.atypon.crud.server.model.Employee;
import com.atypon.crud.server.repo.Repository;
import com.atypon.crud.server.requests.Request;

import java.util.List;

/**
 * An implementation for Query for EmployeeFindAll. for more detailed documentation: {@see Query}.
 */
public class EmployeeFindAllQuery implements Query<List<Employee>> {

  private Repository<Employee> employeeRepository;

  public EmployeeFindAllQuery(Repository<Employee> employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public List<Employee> execute(Request request) {
    return employeeRepository.findAll();
  }
}
