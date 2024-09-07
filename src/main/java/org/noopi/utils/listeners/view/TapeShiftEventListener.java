package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.model.machine.MachineAction;

public interface TapeShiftEventListener extends EventListener {
  void onTapeShifted(MachineAction a);
}
