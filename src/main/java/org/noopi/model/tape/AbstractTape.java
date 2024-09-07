package org.noopi.model.tape;

import javax.swing.event.EventListenerList;

import org.noopi.model.machine.MachineAction;
import org.noopi.model.symbol.Symbol;
import org.noopi.utils.events.tape.TapeMovedEvent;
import org.noopi.utils.events.tape.TapeResetEvent;
import org.noopi.utils.events.tape.TapeWriteEvent;
import org.noopi.utils.listeners.tape.TapeMovedEventListener;
import org.noopi.utils.listeners.tape.TapeResetEventListener;
import org.noopi.utils.listeners.tape.TapeWriteEventListener;
import org.noopi.utils.listeners.view.TapeUpdatedEventListener;

/**
 * Manages all the listener mechanics of a ITape.
 */
public abstract class AbstractTape implements ITape {
  private EventListenerList listenerList;

  protected TapeResetEvent tapeResetEvent;
  protected TapeMovedEvent tapeMovedEvent;
  protected TapeWriteEvent tapeWriteEvent;

  public AbstractTape() {
    listenerList = new EventListenerList();
  }

  /**
   * Sends a <code>TapeResetEvent</code> to every subscribed listeners.
   */
  protected void fireResetEvent() {
    Object[] list = listenerList.getListenerList();
    for (int i = list.length - 2; i >= 0; i -= 2) {
      if (list[i] != TapeResetEventListener.class) {
        continue;
      }
      if (tapeResetEvent == null) {
        tapeResetEvent = new TapeResetEvent();
      }
      ((TapeResetEventListener) list[i + 1]).onTapeReset(tapeResetEvent);
    }
  }

  /**
   * Sends a <code>TapeMovedEvent</code> to every subscribed listeners.
   */
  protected void fireTapeMovedEvent(MachineAction d) {
    assert d != null;
    Object[] list = listenerList.getListenerList();
    boolean b = false;
    for (int i = list.length - 2; i >= 0; i -= 2) {
      if (list[i] != TapeMovedEventListener.class) {
        continue;
      }
      if (tapeMovedEvent == null || !b) {
        tapeMovedEvent = new TapeMovedEvent(d);
        b = true;
      }
      ((TapeMovedEventListener) list[i + 1]).onTapeMoved(tapeMovedEvent);
    }
  }

  /**
   * Sends a <code>TapeWriteEvent</code> to every subscribed listeners.
   */
  protected void fireTapeWriteEvent(Symbol s) {
    Object[] list = listenerList.getListenerList();
    boolean b = false;
    for (int i = list.length - 2; i >= 0; i -= 2) {
      if (list[i] != TapeWriteEventListener.class) {
        continue;
      }
      if (tapeWriteEvent == null || !b) {
        tapeWriteEvent = new TapeWriteEvent(s);
        b = true;
      }
      ((TapeWriteEventListener) list[i + 1]).onTapeWritten(tapeWriteEvent);
    }
  }

  protected void fireTapeUpdatedEvent() {
    Object[] list = listenerList.getListenerList();
    for (int i = list.length - 2; i >= 0; i -= 2) {
      if (list[i] != TapeUpdatedEventListener.class) {
        continue;
      }
      ((TapeUpdatedEventListener) list[i + 1]).onUpdate();
    }
  }

  @Override
  public void addTapeResetEventListener(TapeResetEventListener l) {
    assert l != null;
    listenerList.add(TapeResetEventListener.class, l);
  }

  @Override
  public void addTapeMovedEventListener(TapeMovedEventListener l) {
    assert l != null;
    listenerList.add(TapeMovedEventListener.class, l);
  }

  @Override
  public void addTapeWriteEventListener(TapeWriteEventListener l) {
    assert l != null;
    listenerList.add(TapeWriteEventListener.class, l);
  }

  @Override
  public void addTapeUpdatedEventListener(TapeUpdatedEventListener l) {
    assert l != null;
    listenerList.add(TapeUpdatedEventListener.class, l);
  }

  @Override
  public void removeTapeResetEventListener(TapeResetEventListener l) {
    assert l != null;
    listenerList.remove(TapeResetEventListener.class, l);
  }

  @Override
  public void removeTapeMovedEventListener(TapeMovedEventListener l) {
    assert l != null;
    listenerList.remove(TapeMovedEventListener.class, l);
  }

  @Override
  public void removeTapeWriteEventListener(TapeWriteEventListener l) {
    assert l != null;
    listenerList.remove(TapeWriteEventListener.class, l);
  }
}
