package com.atypon.crud.server.repo.mysql;

import com.atypon.crud.server.model.Employee;
import com.atypon.crud.server.repo.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.atypon.crud.server.utils.SqlConstants.*;

/**
 * An implementation for Repository for Employees by MySQL, for more detailed documentation: {@see
 * Repository}.
 */
public class EmployeeMySqlRepository implements Repository<Employee> {

  private Connection connection;

  public EmployeeMySqlRepository() {}

  /**
   * * Parameterized constructor to initialize the EmployeeMySqlRepository.
   *
   * @param connection the connection to the database
   */
  public EmployeeMySqlRepository(Connection connection) {
    this.connection = connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  public Connection getConnection() {
    return connection;
  }

  @Override
  public boolean insert(Employee... items) {
    validateConnection();
    return Stream.of(items).map(this::insert).anyMatch((isDone) -> !isDone);
  }

  private boolean insert(Employee item) {
    try (PreparedStatement preparedStatement = connection.prepareStatement(EMPLOYEE_INSERT_QUERY)) {
      preparedStatement.setString(1, item.getName());
      preparedStatement.setDouble(2, item.getSalary());
      preparedStatement.setString(3, item.getDepartment());
      return preparedStatement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean insertAll(Collection<Employee> items) {
    validateConnection();
    return items.stream().map(this::insert).anyMatch((isDone) -> !isDone);
  }

  @Override
  public boolean updateById(long id, Employee item) {
    validateConnection();
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(EMPLOYEE_UPDATE_BY_ID_QUERY)) {
      preparedStatement.setString(1, item.getName());
      preparedStatement.setDouble(2, item.getSalary());
      preparedStatement.setString(3, item.getDepartment());
      preparedStatement.setLong(4, id);
      return preparedStatement.executeUpdate() != 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Employee> findAll() {
    validateConnection();
    try (PreparedStatement preparedStatement =
            connection.prepareStatement(EMPLOYEE_FIND_ALL_QUERY);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      return mapToList(resultSet);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Employee> mapToList(ResultSet resultSet) throws SQLException {
    validateConnection();
    List<Employee> employees = new LinkedList<>();
    while (resultSet.next()) {
      employees.add(
          new Employee(
              resultSet.getInt(1),
              resultSet.getString(2),
              resultSet.getDouble(3),
              resultSet.getString(4)));
    }
    return employees;
  }

  @Override
  public Optional<Employee> findById(long id) {
    validateConnection();
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(EMPLOYEE_FIND_BY_ID_QUERY)) {
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<Employee> employees = mapToList(resultSet);
      resultSet.close();
      return (employees.size() == 0) ? Optional.empty() : Optional.of(employees.get(0));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean deleteById(long id) {
    validateConnection();
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(EMPLOYEE_DELETE_BY_ID_QUERY)) {
      preparedStatement.setLong(1, id);
      return preparedStatement.executeUpdate() != 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void validateConnection() {
    if (connection == null) {
      throw new RuntimeException("Connection is null");
    }
  }
}
