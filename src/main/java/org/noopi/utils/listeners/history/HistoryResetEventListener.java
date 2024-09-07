package org.noopi.utils.listeners.history;

import java.util.EventListener;

import org.noopi.utils.events.history.HistoryResetEvent;

public interface HistoryResetEventListener extends EventListener{
  void onHistoryReset(HistoryResetEvent e);
}
