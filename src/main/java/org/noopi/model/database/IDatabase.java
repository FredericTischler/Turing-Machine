package org.noopi.model.database;

import org.noopi.utils.listeners.database.DatabaseRegisterEventListener;
import org.noopi.utils.listeners.database.DatabaseUnregisterEventListener;

public interface IDatabase<R, T> {
  boolean contains(R name);

  T get(R name);

  T[] values();
  R[] entries();

  int size();

  void clear();

  void addDatabaseRegisterEventListener(DatabaseRegisterEventListener<T> l);
  void addDatabaseUnregisterEventListener(DatabaseUnregisterEventListener<T> l);
}
