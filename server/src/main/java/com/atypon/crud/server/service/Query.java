package com.atypon.crud.server.service;

import com.atypon.crud.server.requests.Request;

/**
 * * Query interface to read the state of a part of our system. We Use Query for CQRS (Command Query
 * Responsibility Segregation) CQRS thanks to the separation of the read/query services from the
 * action/command services enables us to do many things.
 *
 * @param <T> the type of read item
 */
public interface Query<T> {

  /**
   * *
   *
   * @param request the Request to determine the type of the request
   * @return the server response
   */
  T execute(Request request);
}
