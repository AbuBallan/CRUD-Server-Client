package com.atypon.crud.server.utils;

/** A Utility class to simplify work with App Constants */
public final class AppConstants {

  public static final int SERVER_PORT = 2000;

  /** * Request Types to limit Requests */
  public enum RequestType {
    FIND_ALL_EMPLOYEES,
    FIND_BY_ID_EMPLOYEE,
    INSERT_EMPLOYEE,
    UPDATE_BY_ID_EMPLOYEE,
    DELETE_BY_ID_EMPLOYEE
  }

  private AppConstants() {}
}
