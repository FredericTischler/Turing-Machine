package org.noopi.model.database;

import java.util.HashMap;

import javax.swing.event.EventListenerList;

import org.noopi.utils.events.database.DatabaseRegisterEvent;
import org.noopi.utils.events.database.DatabaseUnregisterEvent;
import org.noopi.utils.exceptions.DatabaseDuplicateException;
import org.noopi.utils.exceptions.DatabaseMissingEntryException;
import org.noopi.utils.listeners.database.DatabaseRegisterEventListener;
import org.noopi.utils.listeners.database.DatabaseUnregisterEventListener;

public abstract class AbstractDatabase<Identifier, Data>
  implements IMutableDatabase<Identifier, Data>
{
  private HashMap<Identifier, Data> database;

  private EventListenerList listenerList;
  private DatabaseRegisterEvent<Data> registerEvent;
  private DatabaseUnregisterEvent<Data> unregisterEvent;

  protected AbstractDatabase() {
    database = new HashMap<>();
    listenerList = new EventListenerList();
  }

  @Override
  public boolean contains(Identifier name) {
    return database.containsKey(name);
  }

  @Override
  public Data get(Identifier name) {
    return database.get(name);
  }

  @Override
  public final Data registerEntry(Identifier name) throws DatabaseDuplicateException {
    if (database.containsKey(name)) {
      throw new DatabaseDuplicateException();
    }
    Data t = createEntry(name);
    database.put(name, t);
    fireElementRegisteredEvent(t);
    return t;
  }

  @Override
  public void unregisterEntry(Identifier name) throws DatabaseMissingEntryException
  {
    if (!database.containsKey(name)) {
      throw new DatabaseMissingEntryException();
    }
    Data s = database.get(name);
    database.remove(name);
    fireElementUnRegisteredEvent(s);
  }

  @Override
  public void addDatabaseRegisterEventListener(
    DatabaseRegisterEventListener<Data> l
  ) {
    assert l != null;
    listenerList.add(DatabaseRegisterEventListener.class, l);
  }

  @Override
  public void addDatabaseUnregisterEventListener(
    DatabaseUnregisterEventListener<Data> l
  ) {
    assert l != null;
    listenerList.add(DatabaseUnregisterEventListener.class, l);
  }

  @Override
  public Data[] values() {
    return database.values().toArray(assocListTypeInstance());
  }

  @Override
  public Identifier[] entries() {
    return database.keySet().toArray(entryListTypeInstance());
  }

  public IDatabase<Identifier, Data> toReadable() {
    return (IDatabase<Identifier, Data>) this;
  }

  public void clear() {
    if (database.isEmpty()) {
      return;
    }
    try {
      for (Identifier d : entries()) {
        unregisterEntry(d);
      }
    } catch(Exception e) {
      System.err.println("Internal error (database.clear()).");
      e.printStackTrace();
      System.exit(-1);
    }
    assert database.isEmpty();
  }

  public int size() {
    return database.size();
  }

  @SuppressWarnings("unchecked")
  protected void fireElementRegisteredEvent(Data s) {
    Object[] listeners = listenerList.getListenerList();
    boolean b = false;
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] != DatabaseRegisterEventListener.class) {
        continue;
      }
      if (registerEvent == null || !b) {
        registerEvent = new DatabaseRegisterEvent<>(s);
        b = true;
      }
      ((DatabaseRegisterEventListener<Data>) listeners[i + 1])
        .onRegisterEvent(registerEvent);
    }
  }

  @SuppressWarnings("unchecked")
  protected void fireElementUnRegisteredEvent(Data s) {
    Object[] listeners = listenerList.getListenerList();
    boolean b = false;
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] != DatabaseUnregisterEventListener.class) {
        continue;
      }
      if (unregisterEvent == null || !b) {
        unregisterEvent = new DatabaseUnregisterEvent<Data>(s);
        b = true;
      }
      ((DatabaseUnregisterEventListener<Data>) listeners[i + 1])
        .onUnregisterEvent(unregisterEvent);
    }
  }

  protected abstract Data createEntry(Identifier name);

  protected abstract Identifier[] entryListTypeInstance();
  protected abstract Data[] assocListTypeInstance();
}
