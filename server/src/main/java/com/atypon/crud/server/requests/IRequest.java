package com.atypon.crud.server.requests;

import com.atypon.crud.server.utils.AppConstants.RequestType;

public interface IRequest {
  RequestType getRequestType();
}
