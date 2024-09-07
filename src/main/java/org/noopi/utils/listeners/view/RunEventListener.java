package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.RunEvent;

public interface RunEventListener extends EventListener {
  void onRun(RunEvent e);
}
