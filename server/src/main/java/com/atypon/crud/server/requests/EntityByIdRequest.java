package com.atypon.crud.server.requests;

import com.atypon.crud.server.utils.AppConstants.RequestType;

/**
 * * Request that have a id and an entity
 *
 * @param <T> the type of the entity
 */
public class EntityByIdRequest<T> extends ByIdRequest {

  private final T item;

  /**
   * * Parameterized constructor to initialize the ByIdRequest.
   *
   * @param request the type of request
   * @param id the id of the item
   * @param item the new data
   */
  public EntityByIdRequest(RequestType request, long id, T item) {
    super(request, id);
    this.item = item;
  }

  public T getItem() {
    return item;
  }

  @Override
  public String toString() {
    return "EntityRequest{"
        + "id="
        + getId()
        + " ,request="
        + getRequestType()
        + " ,item="
        + item
        + '}';
  }
}
