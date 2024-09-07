package org.noopi.model.history;

import org.noopi.model.transition.Transition;
import org.noopi.utils.listeners.history.HistoryPopEventListener;
import org.noopi.utils.listeners.history.HistoryPushEventListener;
import org.noopi.utils.listeners.history.HistoryResetEventListener;

public interface ITransitionHistory {

  /**
   * Resets the content of the history.
   */
  void reset();

  /**
   * Pushes a transition on top of the history.
   * @param t the transition.
   */
  void pushAction(Transition t);

  /**
   * Pops the transition on top of the history ans returns it.
   * If the history has empty, returns null instead.
   * @return the transition on top of the history.
   */
  Transition popAction();

  /**
   * Tells wether the history has entries or not.
   */
  boolean isEmpty();

  /**
   * When the <code>reset</code> method is called, it will send an
   * <code>HistoryResetEvent</code> event to this listener.
   */
  void addHistoryResetEventListener(HistoryResetEventListener l);

  /**
   * When the <code>push</code> method is called, it will send an
   * <code>HistoryPushEvent</code> event to this listener.
   */
  void addHistoryPushEventListener(HistoryPushEventListener l);

  /**
   * When the <code>pop</code> method is called, it will send an
   * <code>HistoryPopEvent</code> event to this listener.
   */
  void addHistoryPopEventListener(HistoryPopEventListener l);

  void removeHistoryResetEventListener(HistoryResetEventListener l);
  void removeHistoryPushEventListener(HistoryPushEventListener l);
  void removeHistoryPopEventListener(HistoryPopEventListener l);
}
