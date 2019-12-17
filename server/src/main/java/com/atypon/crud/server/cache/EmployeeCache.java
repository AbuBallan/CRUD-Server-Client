package com.atypon.crud.server.cache;

import com.atypon.crud.server.model.Employee;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Function;

/**
 * An implementation for ICache for Employees by HashMap && PriorityQueue, for more detailed
 * documentation: {@see ICache}.
 */
public class EmployeeCache implements ICache<Employee> {

  private static final int MAX_SIZE = 1000;

  private static volatile EmployeeCache INSTANCE;

  private final Map<Long, Cacheable<Employee>> map;

  private final Queue<Long> queue;

  /** * constructor to initialize the EmployeeCache. */
  private EmployeeCache() {
    map = new ConcurrentHashMap<>();
    Comparator<Long> priorityComparator = this::comparePriority;
    queue = new PriorityBlockingQueue<>(MAX_SIZE, priorityComparator);
  }

  public static EmployeeCache getInstance() {
    if (INSTANCE == null) {
      synchronized (EmployeeCache.class) {
        if (INSTANCE == null) INSTANCE = new EmployeeCache();
      }
    }
    return INSTANCE;
  }

  private int comparePriority(Long o1, Long o2) {
    return map.get(o1).compareTo(map.get(o2));
  }

  @Override
  public Optional<Employee> get(long id) {
    return (map.containsKey(id)) ? Optional.of(map.get(id).get()) : Optional.empty();
  }

  @Override
  public Optional<Employee> computeIfAbsent(
      long id, Function<Long, Optional<? extends Employee>> mappingFunction) {

    if (map.containsKey(id)) {
      return Optional.of(map.get(id).get());
    }

    Optional<Cacheable<Employee>> optionalCashable = add(id, mappingFunction);

    if (optionalCashable.isPresent()) {
      Cacheable<Employee> employeeCashable = optionalCashable.get();
      map.put(id, employeeCashable);
      return Optional.of(employeeCashable.get());
    } else {
      return Optional.empty();
    }
  }

  private Optional<Cacheable<Employee>> add(
      Long id, Function<Long, Optional<? extends Employee>> mappingFunction) {
    Optional<? extends Employee> optionalEmployee = mappingFunction.apply(id);

    if (optionalEmployee.isPresent()) {
      Employee employee = optionalEmployee.get();
      if (queue.size() >= MAX_SIZE) {
        Long remove = queue.remove();
        map.remove(remove);
      }
      queue.add(id);
      return Optional.of(Cacheable.of(employee));
    } else {
      return Optional.empty();
    }
  }
}
