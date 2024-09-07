package org.noopi.utils.events.view;

public class ElementAddedEvent {
  private final String s;

  public ElementAddedEvent(String s) {
    this.s = s;
  }

  public String getElement() {
    return s;
  }
}
