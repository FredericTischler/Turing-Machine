package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.OpenFileEvent;

public interface OpenFileEventListener extends EventListener {
  void onFileOpened(OpenFileEvent e);
}
