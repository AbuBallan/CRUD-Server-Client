package com.atypon.crud.server.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/** Database Access Object interface for T */
public interface Repository<T> {

  /**
   * Inserts the T into the database.
   *
   * @param items the items to insert.
   * @return true if the insertion succeeded, false otherwise (already exists);
   */
  boolean insert(T... items);

  /**
   * Inserts Collection of T into the database.
   *
   * @param items the items to insert.
   * @return true if the insertion succeeded, false otherwise (already exists);
   */
  boolean insertAll(Collection<T> items);

  /**
   * * update item by id on the database
   *
   * @param id the item id
   * @param item the new data
   * @return true if the modify succeeded, false otherwise;
   */
  boolean updateById(long id, T item);

  /**
   * Retrieve all the items in the database.
   *
   * @return List of T.
   */
  List<T> findAll();

  /**
   * * Retrieve the item in the database by id
   *
   * @param id the item id
   * @return Optional of T
   */
  Optional<T> findById(long id);

  /**
   * * Delete the item in the database by id
   *
   * @param id the item id
   * @return true if the deletion succeeded, false otherwise;
   */
  boolean deleteById(long id);
}
