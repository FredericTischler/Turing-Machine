package org.noopi.utils.events;

public class AbstractEvent {
  private final Object source;

  public AbstractEvent(Object source) {
    this.source = source;
  }

  public Object getSource() {
    return source;
  }
}
