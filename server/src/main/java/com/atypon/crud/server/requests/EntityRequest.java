package com.atypon.crud.server.requests;

import com.atypon.crud.server.utils.AppConstants.RequestType;

/**
 * * Request that have an entity
 *
 * @param <T> the type of the entity
 */
public class EntityRequest<T> extends Request {

  private final T item;

  /**
   * * Parameterized constructor to initialize the EntityRequest.
   *
   * @param request the type of request
   * @param item the new data
   */
  public EntityRequest(RequestType request, T item) {
    super(request);
    this.item = item;
  }

  public T getItem() {
    return item;
  }

  @Override
  public String toString() {
    return "EntityRequest{" + "request=" + getRequestType() + " ,item=" + item + '}';
  }
}
