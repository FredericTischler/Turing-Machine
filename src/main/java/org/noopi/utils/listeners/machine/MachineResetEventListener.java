package org.noopi.utils.listeners.machine;

import java.util.EventListener;

import org.noopi.utils.events.machine.MachineResetEvent;

public interface MachineResetEventListener extends EventListener {
  void onReset(MachineResetEvent e);
}
