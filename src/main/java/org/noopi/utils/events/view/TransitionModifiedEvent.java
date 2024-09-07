package org.noopi.utils.events.view;

import org.noopi.model.transition.Transition;

public class TransitionModifiedEvent {
  private final Transition t;

  public TransitionModifiedEvent(Transition t) {
    this.t = t;
  }

  public Transition getTransition() {
    return t;
  }
}
