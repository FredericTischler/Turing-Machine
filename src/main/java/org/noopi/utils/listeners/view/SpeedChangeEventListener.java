package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.SpeedChangeEvent;

public interface SpeedChangeEventListener extends EventListener {
  void onSpeedChanged(SpeedChangeEvent e);
}
