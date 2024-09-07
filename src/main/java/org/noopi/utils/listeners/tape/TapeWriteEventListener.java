package org.noopi.utils.listeners.tape;

import java.util.EventListener;

import org.noopi.utils.events.tape.TapeWriteEvent;

public interface TapeWriteEventListener extends EventListener {
  void onTapeWritten(TapeWriteEvent e);
}
