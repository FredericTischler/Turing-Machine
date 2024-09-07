package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.NewFileEvent;

public interface NewFileEventListener extends EventListener {
  void onNewFile(NewFileEvent e);
}
