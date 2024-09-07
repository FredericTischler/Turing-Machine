package org.noopi.utils.listeners.view;

import java.util.EventListener;

import org.noopi.utils.events.view.ElementRemovedEvent;

public interface ElementRemovedEventListener extends EventListener {
  void onElementRemoved(ElementRemovedEvent e);
}
