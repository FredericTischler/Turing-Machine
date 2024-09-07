package org.noopi.view.swing.components.model;

import javax.swing.AbstractListModel;

import org.noopi.model.database.IDatabase;
import org.noopi.utils.events.database.DatabaseRegisterEvent;
import org.noopi.utils.events.database.DatabaseUnregisterEvent;
import org.noopi.utils.listeners.database.DatabaseRegisterEventListener;
import org.noopi.utils.listeners.database.DatabaseUnregisterEventListener;

public class DatabaseListModel<T> extends AbstractListModel<String> {

  private IDatabase<String, T> database;

  public DatabaseListModel(
    IDatabase<String, T> database
  ) {
    this.database = database;
    this.database.addDatabaseRegisterEventListener(
      new DatabaseRegisterEventListener<T>() {
        @Override
        public void onRegisterEvent(DatabaseRegisterEvent<T> e) {
          fireContentsChanged(this, 0, Math.max(0, getSize() - 1));
        }
      }
    );
    this.database.addDatabaseUnregisterEventListener(
      new DatabaseUnregisterEventListener<T>() {
        @Override
        public void onUnregisterEvent(DatabaseUnregisterEvent<T> e) {
          fireContentsChanged(this, 0, Math.max(0, getSize() - 1));
        }
      }
    );
  }

  @Override
  public String getElementAt(int arg0) {
    return database.entries()[arg0];
  }

  @Override
  public int getSize() {
    return database.size();
  }
  
}
