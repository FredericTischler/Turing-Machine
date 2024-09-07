package org.noopi.model.state;

import org.noopi.model.database.AbstractDatabase;

public class StateDatabase extends AbstractDatabase<String, State> {

  private static StateDatabase DATABASE_INSTANCE;

  private static final String[] R = new String[0];
  private static final State[] T = new State[0];
  
  @Override
  protected State createEntry(String name) {
    return new State(name);
  }

  @Override
  protected String[] entryListTypeInstance() {
    return R;
  }

  @Override
  protected State[] assocListTypeInstance() {
    return T;
  }

  public static final StateDatabase getInstance() {
    if (DATABASE_INSTANCE == null) {
      DATABASE_INSTANCE = new StateDatabase();
    }
    return DATABASE_INSTANCE;
  }
}