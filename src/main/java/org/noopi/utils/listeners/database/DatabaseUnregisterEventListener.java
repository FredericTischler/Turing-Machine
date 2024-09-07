package org.noopi.utils.listeners.database;

import java.util.EventListener;

import org.noopi.utils.events.database.DatabaseUnregisterEvent;

public interface DatabaseUnregisterEventListener<T> extends EventListener {
  void onUnregisterEvent(DatabaseUnregisterEvent<T> e);
}
