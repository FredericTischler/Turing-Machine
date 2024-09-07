package org.noopi.model.machine;

import org.noopi.model.state.State;
import org.noopi.model.state.StateDatabase;
import org.noopi.model.symbol.Symbol;
import org.noopi.model.transition.Transition;
import org.noopi.model.transition.TransitionTableModel;
import org.noopi.utils.events.database.DatabaseUnregisterEvent;
import org.noopi.utils.listeners.database.DatabaseUnregisterEventListener;

public final class TuringMachine implements ITuringMachine {

  private State currentState;
  private boolean done;

  public TuringMachine() {
    currentState = State.DEFAULT;
    done = false;

    StateDatabase.getInstance().addDatabaseUnregisterEventListener(
      new DatabaseUnregisterEventListener<State>() {
        @Override
        public void onUnregisterEvent(DatabaseUnregisterEvent<State> e) {
          if (e.getValue().equals(currentState)) {
            // TODO: set current state to State.DEFAULT and fire an event to
            //  warn listeners that the machine is not operable.
          }
        }
      }
    );
  }

  @Override
  public void reset(State defaultState) {
    currentState = defaultState;
    done = false;
  }

  @Override
  public Transition.Right step(Symbol s) {
    assert !done;
    assert s != null;
    assert currentState != State.DEFAULT;
    Transition.Right v = TransitionTableModel.getInstance()
      .getTransition(s, currentState);
    currentState = v.getState();
    if (v.getMachineAction() == MachineAction.MACHINE_STOP) {
      done = true;
    }
    return v.copy();
  }

  @Override
  public void setState(State s) {
    this.currentState = s;
  }

  @Override
  public State getState() {
    return currentState;
  }

  @Override
  public boolean isDone() {
    return done;
  }
}
