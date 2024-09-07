package org.noopi.model.database;

import org.noopi.utils.exceptions.DatabaseDuplicateException;
import org.noopi.utils.exceptions.DatabaseMissingEntryException;

public interface IMutableDatabase<R, T> extends IDatabase<R, T> {

  T registerEntry(R name) throws DatabaseDuplicateException;

  void unregisterEntry(R name) throws DatabaseMissingEntryException;
}
