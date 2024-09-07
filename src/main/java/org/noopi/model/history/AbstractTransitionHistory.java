package org.noopi.model.history;

import javax.swing.event.EventListenerList;

import org.noopi.utils.events.history.HistoryPopEvent;
import org.noopi.utils.events.history.HistoryPushEvent;
import org.noopi.utils.events.history.HistoryResetEvent;

import org.noopi.utils.listeners.history.HistoryPopEventListener;
import org.noopi.utils.listeners.history.HistoryPushEventListener;
import org.noopi.utils.listeners.history.HistoryResetEventListener;

/**
 * Manages all the listener mechanics of a ITransitionHistory.
 */
public abstract class AbstractTransitionHistory implements ITransitionHistory {
  
  private EventListenerList listenerList;

  private HistoryResetEvent historyResetEvent;
  private HistoryPushEvent historyPushEvent;
  private HistoryPopEvent historyPopEvent;

  public AbstractTransitionHistory() {
    listenerList = new EventListenerList();
  }

  /**
   * Sends a <code>HistoryResetEvent</code> to every subscribed listeners.
   */
  protected void fireHistoryResetEvent() {
    Object[] list = listenerList.getListenerList();
    for (int i = list.length - 2; i >= 0; i -= 2) {
      if (list[i] != HistoryResetEventListener.class) {
        continue;
      }
      if (historyResetEvent == null) {
        historyResetEvent = new HistoryResetEvent(this);
      }
      ((HistoryResetEventListener) list[i + 1])
        .onHistoryReset(historyResetEvent);
    }
  }

  /**
   * Sends a <code>HistoryPushEvent</code> to every subscribed listeners.
   */
  protected void fireHistoryPushEvent() {
    Object[] list = listenerList.getListenerList();
    for (int i = list.length - 2; i >= 0; i -= 2) {
      if (list[i] != HistoryPushEventListener.class) {
        continue;
      }
      if (historyPushEvent == null) {
        historyPushEvent = new HistoryPushEvent(this);
      }
      ((HistoryPushEventListener) list[i + 1]).onHistoryPush(historyPushEvent);
    }
  }

  /**
   * Sends a <code>HistoryPopEvent</code> to every subscribed listeners.
   */
  protected void fireHistoryPopEvent() {
    Object[] list = listenerList.getListenerList();
    for (int i = list.length - 2; i >= 0; i -= 2) {
      if (list[i] != HistoryPopEventListener.class) {
        continue;
      }
      if (historyPopEvent == null) {
        historyPopEvent = new HistoryPopEvent(this);
      }
      ((HistoryPopEventListener) list[i + 1]).onHistoryPop(historyPopEvent);
    }
  }

  public void addHistoryResetEventListener(HistoryResetEventListener l) {
    assert l != null;
    listenerList.add(HistoryResetEventListener.class, l);
  }

  public void addHistoryPushEventListener(HistoryPushEventListener l) {
    assert l != null;
    listenerList.add(HistoryPushEventListener.class, l);
  }

  public void addHistoryPopEventListener(HistoryPopEventListener l) {
    assert l != null;
    listenerList.add(HistoryPopEventListener.class, l);
  }

  public void removeHistoryResetEventListener(HistoryResetEventListener l) {
    assert l != null;
    listenerList.remove(HistoryResetEventListener.class, l);
  }

  public void removeHistoryPushEventListener(HistoryPushEventListener l) {
    assert l != null;
    listenerList.remove(HistoryPushEventListener.class, l);
  }

  public void removeHistoryPopEventListener(HistoryPopEventListener l) {
    assert l != null;
    listenerList.remove(HistoryPopEventListener.class, l);
  }

}
