package com.atypon.crud.server.exception;

/** * Exception that indicate to new RequestType that didn't handle */
public class NotFoundRequestException extends RuntimeException {
  public NotFoundRequestException() {
    super("Please handle new RequestType");
  }
}
