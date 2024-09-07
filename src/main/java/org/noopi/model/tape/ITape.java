package org.noopi.model.tape;

import java.io.DataOutputStream;
import java.io.IOException;

import org.noopi.model.machine.MachineAction;
import org.noopi.model.symbol.Symbol;
import org.noopi.utils.listeners.tape.TapeMovedEventListener;
import org.noopi.utils.listeners.tape.TapeResetEventListener;
import org.noopi.utils.listeners.tape.TapeWriteEventListener;
import org.noopi.utils.listeners.view.TapeUpdatedEventListener;

public interface ITape {

  /**
   * Every cell of the tape are now <code>defaultSymbol</code>.
   */
  void reset();

  /**
   * The tape cells are now of [..., <code>defaultSymbols</code>,
   * <code>symbols</code>, <code>defaultSymbol</code>, ...] and the tape pointer
   * is set on the first symbol of <code>symbols</code>.
   * @param defaultSymbol the default symbol on the tape.
   * @param symbols the sequence of symbol under the pointer.
   */
  void reset(Symbol[] symbols);

  /**
   * Moves the tape on a given direction
   * (thus moving the pointer to the opposite direction).
   */
  void shift(MachineAction d);

  /**
   * @return the symbol under the tape pointer.
   */
  Symbol readSymbol();

  /**
   * Writes <code>symbol</code> under the tape pointer.
   * @param symbol the symbol to be written.
   */
  void writeSymbol(Symbol symbol);

  /**
   * Gets a slice of <code>2 * spanWidth + 1</code> cells of the tape under the
   * head. <code>spanWidth</code> must be greater or equal than 0.
   */
  Symbol[] getSlice(int spanWidth);

  /**
   * TODO: describe
   * @param dos
   */
  void save(DataOutputStream dos, Symbol[] symbols) throws IOException;

  /**
   * TODO: describe
   */
  void from(ITape o);

  /**
   * TODO: describe
   * @param s
   */
  void removeAllOccurencesOfSymbol(Symbol s);

  /**
   * When the <code>reset</code> method is called, it will send a
   * <code>TapeResetEvent</code> event to this listener.
   */
  void addTapeResetEventListener(TapeResetEventListener l);

  /**
   * When the <code>shiftLeft</code> or <code>shiftRight</code> methods are
   * called, it will send a <code>TapeMovedEvent</code> event to this listener.
   */
  void addTapeMovedEventListener(TapeMovedEventListener l);

  /**
   * When the <code>writeSymbol</code> method is called, it will send an
   * <code>TapeWriteEvent</code> event to this listener.
   */
  void addTapeWriteEventListener(TapeWriteEventListener l);

  /**
   * TODO: describe
   */
  void addTapeUpdatedEventListener(TapeUpdatedEventListener l);

  void removeTapeResetEventListener(TapeResetEventListener l);
  void removeTapeMovedEventListener(TapeMovedEventListener l);
  void removeTapeWriteEventListener(TapeWriteEventListener l);
}
