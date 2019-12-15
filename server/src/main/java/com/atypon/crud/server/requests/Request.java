package com.atypon.crud.server.requests;

import com.atypon.crud.server.utils.AppConstants.RequestType;

/** * Basic Request */
public class Request implements IRequest {

  private final RequestType requestType;

  /**
   * * Parameterized constructor to initialize the Request.
   *
   * @param request the type of request
   */
  public Request(RequestType request) {
    this.requestType = request;
  }

  public RequestType getRequestType() {
    return requestType;
  }

  @Override
  public String toString() {
    return "Request{" + "requestType=" + requestType + '}';
  }
}
