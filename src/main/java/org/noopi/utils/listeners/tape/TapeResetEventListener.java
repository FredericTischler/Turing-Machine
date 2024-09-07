package org.noopi.utils.listeners.tape;

import java.util.EventListener;

import org.noopi.utils.events.tape.TapeResetEvent;

public interface TapeResetEventListener extends EventListener {
  void onTapeReset(TapeResetEvent e);
}
