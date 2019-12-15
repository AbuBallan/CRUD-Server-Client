package com.atypon.crud.server.exception;

/** * Exception that indicate to Maximum pool size reached. */
public class ConnectionOverflowException extends RuntimeException {

  public ConnectionOverflowException() {
    super("Maximum pool size reached, no available connections!");
  }
}
