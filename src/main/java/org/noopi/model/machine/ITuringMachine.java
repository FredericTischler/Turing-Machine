package org.noopi.model.machine;

import org.noopi.model.state.State;
import org.noopi.model.symbol.Symbol;
import org.noopi.model.transition.Transition;

public interface ITuringMachine {

  /**
   * Given a symbol <code>s</code>, changes it's state and returns the outcome
   * (new state, new symbol and the action done).
   * @param s the symbol.
   * @return The outcome of the step.
   */
  Transition.Right step(Symbol s);

  /**
   * Sets the current state of the machine to <code>s</code>.
   */
  void setState(State s);

  /**
   * @return the current state.
   */
  State getState();

  /**
   * Tells wether the machine has finished it's calculations or not.
   */
  boolean isDone();

  /**
   * Sets the machine state to <code>s</code> and <code>isDone()</code> returns
   * false.
   */
  void reset(State s);
}
