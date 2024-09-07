package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.ElementAddedEvent;

public interface ElementAddedEventListener extends EventListener {
  void onElementAdded(ElementAddedEvent e);
}
