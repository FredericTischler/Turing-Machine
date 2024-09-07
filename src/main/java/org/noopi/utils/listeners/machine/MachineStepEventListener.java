package org.noopi.utils.listeners.machine;

import java.util.EventListener;

import org.noopi.utils.events.machine.MachineStepEvent;

public interface MachineStepEventListener extends EventListener {
  void onMachineStepped(MachineStepEvent e);
}
