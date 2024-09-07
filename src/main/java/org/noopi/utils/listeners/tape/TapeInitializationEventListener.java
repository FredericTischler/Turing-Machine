package org.noopi.utils.listeners.tape;

import java.util.EventListener;

import org.noopi.utils.events.tape.TapeInitializationEvent;

public interface TapeInitializationEventListener extends EventListener {
  void onTapeInitialized(TapeInitializationEvent e);
}
