package com.atypon.crud.client.utils;

/** A Utility class to simplify work with App Constants */
public final class AppConstants {

  public static final String HOST = "localhost";

  public static final int PORT = 2000;

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
