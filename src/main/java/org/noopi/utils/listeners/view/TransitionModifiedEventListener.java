package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.TransitionModifiedEvent;

public interface TransitionModifiedEventListener extends EventListener {
  void onTransitionModified(TransitionModifiedEvent e);
}
