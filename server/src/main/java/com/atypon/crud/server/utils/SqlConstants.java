package com.atypon.crud.server.utils;

/** A Utility class to simplify work with SQL Statements Constants */
public final class SqlConstants {

  public static final String EMPLOYEE_INSERT_QUERY =
      "INSERT INTO Employee (name,salary,department) VALUES(?,?,?)";

  public static final String EMPLOYEE_UPDATE_BY_ID_QUERY =
      "UPDATE Employee SET name=? ,salary=? ,department=? WHERE id=?";

  public static final String EMPLOYEE_FIND_ALL_QUERY = "SELECT * FROM Employee";

  public static final String EMPLOYEE_FIND_BY_ID_QUERY = "SELECT * FROM Employee WHERE id=?";

  public static final String EMPLOYEE_DELETE_BY_ID_QUERY = "DELETE FROM Employee WHERE id=?";

  private SqlConstants() {}
}
