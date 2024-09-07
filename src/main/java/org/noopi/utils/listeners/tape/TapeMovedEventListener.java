package org.noopi.utils.listeners.tape;

import java.util.EventListener;

import org.noopi.utils.events.tape.TapeMovedEvent;

public interface TapeMovedEventListener extends EventListener {
  void onTapeMoved(TapeMovedEvent e);
}
