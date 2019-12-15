package com.atypon.crud.client.data.db;

import com.atypon.crud.client.requests.ByIdRequest;
import com.atypon.crud.client.requests.EntityRequest;
import com.atypon.crud.client.requests.Request;

import java.util.List;
import java.util.Optional;

/**
 * * DBHelper used to simplify work with the database server.
 *
 * @param <T> the type of data
 */
public interface DBHelper<T> {

  /**
   * Inserts the T into the database.
   *
   * @param request the request to insert.
   * @return true if the insertion succeeded, false otherwise (already exists);
   */
  boolean insert(EntityRequest<T> request);

  /**
   * * update item by id on the database
   *
   * @param request the request to update by id
   * @return true if the modify succeeded, false otherwise;
   */
  boolean updateById(ByIdRequest request);

  /**
   * Retrieve all the items in the database.
   *
   * @param request the request to find all
   * @return List of T.
   */
  List<T> findAll(Request request);

  /**
   * * Retrieve the item in the database by id
   *
   * @param request the request to find by id
   * @return Optional of T
   */
  Optional<T> findById(ByIdRequest request);

  /**
   * * Delete the item in the database by id
   *
   * @param request the request to delete by id
   * @return true if the deletion succeeded, false otherwise;
   */
  boolean deleteById(ByIdRequest request);
}
