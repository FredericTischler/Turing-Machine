package org.noopi.utils.listeners.history;

import java.util.EventListener;

import org.noopi.utils.events.history.HistoryPopEvent;

public interface HistoryPopEventListener extends EventListener {
  void onHistoryPop(HistoryPopEvent e);
}
