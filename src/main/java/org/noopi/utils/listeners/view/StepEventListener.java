package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.StepEvent;

public interface StepEventListener extends EventListener {
  void onStep(StepEvent e);
}
