package org.noopi.model.symbol;

import org.noopi.model.database.AbstractDatabase;

public class SymbolDatabase extends AbstractDatabase<String, Symbol> {

  private static SymbolDatabase DATABASE_INSTANCE;

  private static final String[] R = new String[0];
  private static final Symbol[] T = new Symbol[0];

  @Override
  protected Symbol createEntry(String name) {
    return new Symbol(name);
  }

  @Override
  protected String[] entryListTypeInstance() {
    return R;
  }

  @Override
  protected Symbol[] assocListTypeInstance() {
    return T;
  }

  public static final SymbolDatabase getInstance() {
    if (DATABASE_INSTANCE == null) {
      DATABASE_INSTANCE = new SymbolDatabase();
    }
    return DATABASE_INSTANCE;
  }
}
