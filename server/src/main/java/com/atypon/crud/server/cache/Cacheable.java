package com.atypon.crud.server.cache;

import java.util.Objects;

/**
 * * Cacheable Class is the Value Holder for the Cache
 *
 * @param <T> the type of the item
 */
public class Cacheable<T> implements Comparable<Cacheable<T>> {

  private final T value;

  private long usages;

  private long lastTimeUsed;

  private Cacheable(T value) {
    this.value = value;
    this.usages = 0;
  }

  /**
   * * static factory method to create Cacheable Object
   *
   * @param item the value of Cacheable
   * @param <E> the type of the value
   * @return Cacheable Object
   */
  public static <E> Cacheable<E> of(E item) {
    return new Cacheable<>(item);
  }

  /**
   * *
   *
   * @return the value of the object
   */
  public T get() {
    this.usages++;
    this.lastTimeUsed = System.currentTimeMillis();
    return value;
  }

  /**
   * *
   *
   * @return how many times is used
   */
  public long getUsages() {
    return usages;
  }

  /**
   * *
   *
   * @return the last time is used
   */
  public long getLastTimeUsed() {
    return lastTimeUsed;
  }

  @Override
  public int compareTo(Cacheable<T> o) {
    long priority = usages / (System.currentTimeMillis() - lastTimeUsed);
    long oPriority = o.getUsages() / (System.currentTimeMillis() - o.getLastTimeUsed());
    return (int) (priority - oPriority);
  }

  @Override
  public String toString() {
    return "Cacheable{"
        + "value="
        + value
        + ", usages="
        + usages
        + ", lastTimeUsed="
        + lastTimeUsed
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cacheable<?> cacheable = (Cacheable<?>) o;
    return getUsages() == cacheable.getUsages()
        && getLastTimeUsed() == cacheable.getLastTimeUsed()
        && value.equals(cacheable.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, getUsages(), getLastTimeUsed());
  }
}
