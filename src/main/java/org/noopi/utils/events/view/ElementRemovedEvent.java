package org.noopi.utils.events.view;

public class ElementRemovedEvent {
  private final String s;

  public ElementRemovedEvent(String s) {
    this.s = s;
  }

  public String getElement() {
    return s;
  }
}
