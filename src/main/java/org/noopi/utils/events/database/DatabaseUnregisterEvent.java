package org.noopi.utils.events.database;

public class DatabaseUnregisterEvent<T> {
  private final T t;

  public DatabaseUnregisterEvent(T t) {
    assert t != null;
    this.t = t;
  }

  public T getValue() {
    return t;
  }
}
