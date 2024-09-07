package org.noopi.utils.listeners.history;

import java.util.EventListener;

import org.noopi.utils.events.history.HistoryPushEvent;

public interface HistoryPushEventListener extends EventListener {
  void onHistoryPush(HistoryPushEvent e);
}
