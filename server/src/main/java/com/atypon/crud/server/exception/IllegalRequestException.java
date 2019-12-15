package com.atypon.crud.server.exception;

/** * Exception that indicate to Illegal Request Type */
public class IllegalRequestException extends RuntimeException {
  public IllegalRequestException() {
    super("Illegal Request Type");
  }
}
