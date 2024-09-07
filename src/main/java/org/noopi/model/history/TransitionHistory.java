package org.noopi.model.history;

import java.util.Arrays;

import org.noopi.model.transition.Transition;

public class TransitionHistory extends AbstractTransitionHistory {

  private static final int DEFAULT_HISTORY_SIZE = 32;

  private Transition[] history;
  private int index;

  public TransitionHistory() {
    reset();
  }

  @Override
  public void reset() {
    history = new Transition[DEFAULT_HISTORY_SIZE];
    index = 0;
    fireHistoryResetEvent();
  }

  @Override
  public void pushAction(Transition a) {
    assert a != null;
    if (history.length == index) {
      history = Arrays.copyOf(history, history.length * 2);
    }
    history[index] = a;
    ++index;
    fireHistoryPushEvent();
  }

  @Override
  public Transition popAction() {
    assert index > 0;
    --index;
    Transition a = history[index];
    if (index == history.length / 4) {
      history = Arrays.copyOf(history, history.length / 4);
    }
    fireHistoryPopEvent();
    return a;
  }

  @Override
  public boolean isEmpty() {
    return index == 0;
  }
}
