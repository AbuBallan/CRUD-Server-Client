package com.atypon.crud.server.cache;

import java.util.Optional;
import java.util.function.Function;

/**
 * * Cache interface that save item which have more usage and recently used
 *
 * @param <T> the type of the items in the cache
 */
public interface ICache<T> {

  /**
   * * get item from cache if is exist
   *
   * @param id the id of the item
   * @return Optional of Item that maybe have value
   */
  Optional<T> get(long id);

  /**
   * * get item from cache if is exist or get it from the database
   *
   * @param id the id of the item
   * @param mappingFunction to show how to get Item from the database
   * @return Optional of Item that maybe have value
   */
  Optional<T> computeIfAbsent(long id, Function<Long, Optional<? extends T>> mappingFunction);
}
