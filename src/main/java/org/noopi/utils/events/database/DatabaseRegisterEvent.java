package org.noopi.utils.events.database;

public class DatabaseRegisterEvent<T> {
  private final T t;

  public DatabaseRegisterEvent(T t) {
    assert t != null;
    this.t = t;
  }

  public T getValue() {
    return t;
  }
  
}
