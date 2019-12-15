package com.atypon.crud.server.model;

import java.util.Objects;

/**
 * * Employee model that contains id (optional), name (required), salary (required), department
 * (required)
 */
public class Employee {

  private int id;

  private final String name;

  private final double salary;

  private final String department;

  /**
   * * constructor to initialize the Employee.
   *
   * @param id the employee id
   * @param name the employee name
   * @param salary the employee salary
   * @param department the employee department
   */
  public Employee(int id, String name, double salary, String department) {
    this.id = id;
    this.name = name;
    this.salary = salary;
    this.department = department;
  }

  /**
   * * constructor to initialize the Employee.
   *
   * @param name the employee name
   * @param salary the employee salary
   * @param department the employee department
   */
  public Employee(String name, double salary, String department) {
    this.name = name;
    this.salary = salary;
    this.department = department;
  }

  /**
   * * static factory method to initialize the Employee.
   *
   * @param id the employee id
   * @param name the employee name
   * @param salary the employee salary
   * @param department the employee department
   */
  public static Employee of(int id, String name, double salary, String department) {
    return new Employee(id, name, salary, department);
  }

  /**
   * * static factory method to initialize the Employee.
   *
   * @param name the employee name
   * @param salary the employee salary
   * @param department the employee department
   */
  public static Employee of(String name, double salary, String department) {
    return new Employee(name, salary, department);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getSalary() {
    return salary;
  }

  public String getDepartment() {
    return department;
  }

  @Override
  public String toString() {
    return "Employee{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", salary="
        + salary
        + ", department='"
        + department
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return getId() == employee.getId()
        && Double.compare(employee.getSalary(), getSalary()) == 0
        && getName().equals(employee.getName())
        && getDepartment().equals(employee.getDepartment());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getSalary(), getDepartment());
  }
}
