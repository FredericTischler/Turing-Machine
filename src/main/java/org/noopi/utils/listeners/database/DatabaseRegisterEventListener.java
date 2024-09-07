package org.noopi.utils.listeners.database;

import java.util.EventListener;

import org.noopi.utils.events.database.DatabaseRegisterEvent;

public interface DatabaseRegisterEventListener<T> extends EventListener {
  void onRegisterEvent(DatabaseRegisterEvent<T> e);
}
