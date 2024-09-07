package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.SaveEvent;

public interface SaveEventListener extends EventListener {
  void onSave(SaveEvent e);
}
