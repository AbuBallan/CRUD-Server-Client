package com.atypon.crud.server.requests;

import com.atypon.crud.server.utils.AppConstants.RequestType;

/** * Request that have a id */
public class ByIdRequest extends Request {

  private final long id;

  /**
   * * Parameterized constructor to initialize the ByIdRequest.
   *
   * @param request the type of request
   * @param id the id of the item
   */
  public ByIdRequest(RequestType request, long id) {
    super(request);
    this.id = id;
  }

  public long getId() {
    return id;
  }

  @Override
  public String toString() {
    return "ByIdRequest{" + "request=" + getRequestType() + " ,id=" + id + '}';
  }
}
