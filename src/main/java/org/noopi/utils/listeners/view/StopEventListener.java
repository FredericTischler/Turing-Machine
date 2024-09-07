package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.StopEvent;

public interface StopEventListener extends EventListener {
  void onStop(StopEvent e);
}
