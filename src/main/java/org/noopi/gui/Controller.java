package org.noopi.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.noopi.model.tape.ITape;
import org.noopi.model.tape.Tape;
import org.noopi.model.transition.Transition;
import org.noopi.model.transition.TransitionTableModel;
import org.noopi.utils.exceptions.DatabaseDuplicateException;
import org.noopi.utils.exceptions.DatabaseMissingEntryException;
import org.noopi.utils.events.database.DatabaseUnregisterEvent;
import org.noopi.utils.events.history.HistoryPopEvent;
import org.noopi.utils.events.history.HistoryPushEvent;
import org.noopi.utils.events.history.HistoryResetEvent;
import org.noopi.utils.events.view.ElementAddedEvent;
import org.noopi.utils.events.view.ElementRemovedEvent;
import org.noopi.utils.events.view.NewFileEvent;
import org.noopi.utils.events.view.OpenFileEvent;
import org.noopi.utils.events.view.RunEvent;
import org.noopi.utils.events.view.SaveEvent;
import org.noopi.utils.events.view.SpeedChangeEvent;
import org.noopi.utils.events.view.StepEvent;
import org.noopi.utils.events.view.StopEvent;
import org.noopi.utils.Utils;
import org.noopi.utils.events.tape.TapeInitializationEvent;
import org.noopi.utils.listeners.tape.TapeInitializationEventListener;
import org.noopi.utils.listeners.view.ActiveMachineListener;
import org.noopi.utils.listeners.view.ElementAddedEventListener;
import org.noopi.utils.listeners.view.ElementRemovedEventListener;
import org.noopi.utils.listeners.database.DatabaseUnregisterEventListener;
import org.noopi.utils.listeners.history.HistoryPopEventListener;
import org.noopi.utils.listeners.history.HistoryPushEventListener;
import org.noopi.utils.listeners.history.HistoryResetEventListener;
import org.noopi.utils.listeners.view.SpeedChangeEventListener;
import org.noopi.utils.listeners.view.StepEventListener;
import org.noopi.utils.listeners.view.StopEventListener;
import org.noopi.utils.listeners.view.InitialTapeSymbolWrittenEventListener;
import org.noopi.utils.listeners.view.MachineInitialStateChangedEventListener;
import org.noopi.utils.listeners.view.NewFileEventListener;
import org.noopi.utils.listeners.view.OpenFileEventListener;
import org.noopi.utils.listeners.view.RunEventListener;
import org.noopi.utils.listeners.view.SaveEventListener;
import org.noopi.utils.listeners.view.TapeShiftEventListener;
import org.noopi.model.history.ITransitionHistory;
import org.noopi.model.history.TransitionHistory;
import org.noopi.model.machine.ITuringMachine;
import org.noopi.model.machine.MachineAction;
import org.noopi.model.machine.TuringMachine;
import org.noopi.model.state.State;
import org.noopi.model.state.StateDatabase;
import org.noopi.model.symbol.Symbol;
import org.noopi.model.symbol.SymbolDatabase;
import org.noopi.view.IWindow;
import org.noopi.view.swing.SwingWindow;

public final class Controller {

  // ATTRIBUTES

  private final int SECOND_CONV = 100;

  // Model
  private ITuringMachine machine;
  private State initialMachineState;
  private ITape tape;
  private ITape initialTape;
  private ITransitionHistory history;

  private Timer machineTimer;

  // View
  private JFrame frame;
  private IWindow layout;

  public Controller() {
    createModel();
    createView();
    placeComponents();
    createController();
  }

  public void display() {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.pack();
  }

  @SuppressWarnings("unused")
  private void debug() {
    try {
      // Three state busy beaver
      State  state_a  = StateDatabase .getInstance().registerEntry("a");
      State  state_b  = StateDatabase .getInstance().registerEntry("b");
      State  state_c  = StateDatabase .getInstance().registerEntry("c");
      Symbol symbol_0 = SymbolDatabase.getInstance().registerEntry("0");
      Symbol symbol_1 = SymbolDatabase.getInstance().registerEntry("1");
      TransitionTableModel.getInstance().update(new Transition(state_a, symbol_0, MachineAction.TAPE_LEFT   , state_b, symbol_1));
      TransitionTableModel.getInstance().update(new Transition(state_b, symbol_0, MachineAction.TAPE_LEFT   , state_c, symbol_0));
      TransitionTableModel.getInstance().update(new Transition(state_c, symbol_0, MachineAction.TAPE_RIGHT  , state_c, symbol_1));
      TransitionTableModel.getInstance().update(new Transition(state_a, symbol_1, MachineAction.MACHINE_STOP, state_a, symbol_1));
      TransitionTableModel.getInstance().update(new Transition(state_b, symbol_1, MachineAction.TAPE_LEFT   , state_b, symbol_1));
      TransitionTableModel.getInstance().update(new Transition(state_c, symbol_1, MachineAction.TAPE_RIGHT  , state_a, symbol_1));
      for (int i = 0; i < 8; ++i) {
        initialTape.writeSymbol(symbol_0);
        initialTape.shift(MachineAction.TAPE_RIGHT);
      }
      for (int i = 0; i < 6; ++i) {
        initialTape.shift(MachineAction.TAPE_LEFT);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  private void createModel() {
    tape = new Tape();
    initialTape = new Tape();
    machine = new TuringMachine();
    history = new TransitionHistory();
    machineTimer = new Timer(0, new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        stepMachine();
        if (machine.isDone()) {
          machineTimer.stop();
        }
      }
    });
    // DEBUG
    // debug();
  }

  private void createView() {
    frame = new JFrame();
    layout = new SwingWindow(tape, initialTape);
  }

  private void placeComponents() {
    frame.setContentPane(layout.getView());
    frame.setJMenuBar(layout.getMenuBar());
  }

  private void createSymbolController() {
    layout.addSymbolRegisteredEventListener(new ElementAddedEventListener() {
      @Override
      public void onElementAdded(ElementAddedEvent e) {
        String element = e.getElement();
        try {
          if (!SymbolDatabase.getInstance().contains(element))
            SymbolDatabase.getInstance().registerEntry(element);
          else
            // TODO: add accents
            layout.showError(
              "Le symbole \"" + element + "\" est deja entregistre !"
            );
        } catch (DatabaseDuplicateException e1) {
          // Should never happen
          e1.printStackTrace();
        }
      }
    });

    layout.addSymbolUnRegisteredEventListener(new ElementRemovedEventListener() {
      @Override
      public void onElementRemoved(ElementRemovedEvent e) {
        String element = e.getElement();
        try {
          if (SymbolDatabase.getInstance().contains(element))
            SymbolDatabase.getInstance().unregisterEntry(e.getElement());
          else
            // TODO: add accents
            layout.showError(
              "Le symbole \"" + element + "\" n'a jamais ete enregistre !"
            );
        } catch (DatabaseMissingEntryException e1) {
          // Should never happen
          e1.printStackTrace();
        }
      }
    });
  }

  private void createStateController() {
    layout.addStateRegisteredEventListener(new ElementAddedEventListener() {
      @Override
      public void onElementAdded(ElementAddedEvent e) {
        String element = e.getElement();
        try {
          if (!StateDatabase.getInstance().contains(element))
            StateDatabase.getInstance().registerEntry(element);
          else
            // TODO: add accents
            layout.showError(
              "L'etat \"" + element + "\" est deja entregistre !"
            );
        } catch (DatabaseDuplicateException e1) {
          // Should never happen
          e1.printStackTrace();
        }
      }
    });
    
    layout.addStateUnRegisteredEventListener(new ElementRemovedEventListener() {
      @Override
      public void onElementRemoved(ElementRemovedEvent e) {
        String element = e.getElement();
        try {
          if (StateDatabase.getInstance().contains(element))
            StateDatabase.getInstance().unregisterEntry(e.getElement());
          else
            // TODO: add accents
            layout.showError(
              "L'etat \"" + element + "\" n'a jamais ete enregistre !"
            );
        } catch (DatabaseMissingEntryException e1) {
          // Should never happen
          e1.printStackTrace();
        }
      }
    });
  }

  private void createInitialTapeController() {
    layout.addInitialTapeShiftEventListener(new TapeShiftEventListener() {
      @Override
      public void onTapeShifted(MachineAction a) {
        initialTape.shift(a);
      }
    });

    layout.addInitialTapeSymbolWrittenEventListener(
      new InitialTapeSymbolWrittenEventListener() {
        @Override
        public void onTapeWritten(Symbol s) {
          if (s == null) {
            return;
          }
          initialTape.writeSymbol(s);
        }
      }
    );
  }

  private void createTapeController() {
    layout.addTapeInitializationEventListener(
      new TapeInitializationEventListener() {
        @Override
        public void onTapeInitialized(TapeInitializationEvent e) {
          tape.from(initialTape);
        }
    });

    SymbolDatabase.getInstance().addDatabaseUnregisterEventListener(
      new DatabaseUnregisterEventListener<Symbol>() {
        @Override
        public void onUnregisterEvent(DatabaseUnregisterEvent<Symbol> e) {
          initialTape.removeAllOccurencesOfSymbol(e.getValue());
          tape.removeAllOccurencesOfSymbol(e.getValue());
        }
      }
    );
  }

  private void createMachineController() {
    layout.addSpeedChangeEventListener(new SpeedChangeEventListener() {
      @Override
      public void onSpeedChanged(SpeedChangeEvent e) {
        final double ratio = e.getSpeed();
        final int integerRatio = (int) (ratio * 100);
        final int delay = (100 - integerRatio) * SECOND_CONV;
        machineTimer.setDelay(delay);
      }
    });

    layout.addStepEventListener(new StepEventListener() {
      @Override
      public void onStep(StepEvent e) {
        stepMachine();
      }
    });

    layout.addActiveMachineListener(new ActiveMachineListener() {
      @Override
      public void onActiveStateChange(boolean active) {
        if (active) {
          tape.from(initialTape);
          machine.reset(initialMachineState);
          layout.setMachineState(initialMachineState);
        } else {
          layout.setMachineState(State.DEFAULT); // ?
        }
      }
    });

    layout.addMachineInitialStateChangedEventListener(
      new MachineInitialStateChangedEventListener() {
        @Override
        public void onInitialStateChanged(State state) {
          initialMachineState = state;
        }
      }
    );

    layout.addRunEventListener(new RunEventListener() {
      @Override
      public void onRun(RunEvent e) {
        if (!machineTimer.isRunning()) machineTimer.start();
      }
    });

    layout.addStopEventListener(new StopEventListener() {
      @Override
      public void onStop(StopEvent e) {
        if (machineTimer.isRunning()) machineTimer.stop();
      }
    });
  }

  private void createHistoryController() {
    history.addHistoryPushEventListener(new HistoryPushEventListener() {
      @Override
      public void onHistoryPush(HistoryPushEvent e) {
        // TODO: fix
      }
    });

    history.addHistoryPopEventListener(new HistoryPopEventListener() {
      @Override
      public void onHistoryPop(HistoryPopEvent e) {
        // TODO: fix
      }
    });

    history.addHistoryResetEventListener(new HistoryResetEventListener() {
      @Override
      public void onHistoryReset(HistoryResetEvent e) {
        // TODO: fix
      }
    });
  }

  private void createController() {
    createSymbolController();
    createStateController();
    createInitialTapeController();
    createTapeController();
    createMachineController();
    createHistoryController();

    layout.addOpenFileEventListener(new OpenFileEventListener() {
      @Override
      public void onFileOpened(OpenFileEvent e) {
        File f = layout.openFile(new File("."));
        if (f == null || !f.exists() || f.isDirectory()) {
          // TODO: accents
          layout.showError("Le fichier selectionne est incorrect !");
          return;
        }
        if (loadMachineFromFile(f)) {
          layout.showError("Impossible d'ouvrir le fichier !");
          return;
        }
      }
    });

    layout.addSaveEventListener(new SaveEventListener() {
      @Override
      public void onSave(SaveEvent e) {
        File f = layout.selectFileToSave(new File("."));
        if (f == null || f.isDirectory()) {
          // TODO: accents
          layout.showError("Le fichier selectionne est incorrect !");
          return;
        }
        if (saveMachineToFile(f)) {
          layout.showError("Impossible d'ouvrir le fichier !");
          return;
        }
      }
    });

    layout.addNewFileEventListener(new NewFileEventListener() {
      @Override
      public void onNewFile(NewFileEvent e) {
        // TODO: accents
        if (
          ( SymbolDatabase.getInstance().size() != 0
            || StateDatabase.getInstance().size() != 0)
          && !layout.showConfirmDialog(
            "Vous allez perdre votre travail.\nEtes vous sur ?"
          )
        ) {
          return;
        }
        tape.reset();
        initialTape.reset();
        SymbolDatabase.getInstance().clear();
        StateDatabase.getInstance().clear();
      }
    });
  }

  private void stepMachine() {
    Symbol s = tape.readSymbol();
    Transition.Right result = machine.step(s);
    tape.writeSymbol(result.getSymbol());
    switch (result.getMachineAction()) {
      case MACHINE_STOP: layout.showInformation("La machine s'arrete"); break;
      case TAPE_LEFT: tape.shift(MachineAction.TAPE_RIGHT); break;
      case TAPE_RIGHT: tape.shift(MachineAction.TAPE_LEFT); break;
    }
    layout.setMachineState(result.getState());
  }

  private boolean loadMachineFromFile(File f) {
    SymbolDatabase.getInstance().clear();
    StateDatabase.getInstance().clear();
    tape.reset();
    initialTape.reset();
    initialMachineState = null;
    try (
      DataInputStream dis = new DataInputStream(new FileInputStream(f))
    ) {
      String[] symbols = new String[dis.readInt()];
      String[] states = new String[dis.readInt()];
      Symbol[] actualSymbols = new Symbol[symbols.length];
      State[] actualStates = new State[states.length];

      // Symbols
      for (int i = 0; i < symbols.length; ++i) {
        String symbol = dis.readUTF();
        if (
          symbol == null
          || symbol.equals("")
          || SymbolDatabase.getInstance().contains(symbol)
        )
        { throw new Exception("duplicate symbol"); }
        symbols[i] = symbol;
        actualSymbols[i] = SymbolDatabase.getInstance().registerEntry(symbol);
      }

      // States
      for (int i = 0; i < states.length; ++i) {
        String state = dis.readUTF();
        if (
          state == null
          || state.equals("")
          || StateDatabase.getInstance().contains(state)
        )
        { throw new Exception("duplicate state"); }
        states[i] = state;
        actualStates[i] = StateDatabase.getInstance().registerEntry(state);
      }

      // Tape
      int tapeSize = dis.readInt();
      int tapeOffset = dis.readInt();
      System.out.println("tape offset : " + tapeOffset);

      for (int i = 0; i < tapeSize; ++i) {
        int pointer = dis.readInt();
        if ((pointer < 0 && pointer != -1) || pointer >= symbols.length) {
          throw new Exception("Unknown tape symbol : index " + pointer);
        }
        initialTape.writeSymbol(
          pointer == -1
          ? Symbol.DEFAULT
          : SymbolDatabase.getInstance().get(symbols[pointer])
        );
        initialTape.shift(MachineAction.TAPE_RIGHT);
      }
      for (int i = 0; i < tapeOffset; ++i) {
        initialTape.shift(MachineAction.TAPE_LEFT);
      }

      // Transition table
      for (int i = 0; i < symbols.length; i++) {
        for (int j = 0; j < states.length; j++) {
          final int stateIndex = dis.readInt();
          final int action = dis.readInt();
          final int symbolIndex = dis.readInt();
          if (stateIndex < 0 || stateIndex > states.length) {
            throw new Exception("Unknown transition state");
          }
          if (action < 0 || action > MachineAction.values().length) {
            throw new Exception("Unknown transition action");
          }
          if (symbolIndex < 0 || symbolIndex > symbols.length) {
            throw new Exception("Unknown transition symbol");
          }
          TransitionTableModel.getInstance().update(new Transition(
            actualStates[j],
            actualSymbols[i],
            MachineAction.values()[action],
            actualStates[stateIndex],
            actualSymbols[symbolIndex]
          ));
        }
      }
    } catch (Exception e) {
      System.err.println("Error while loading the file :");
      e.printStackTrace();
      SymbolDatabase.getInstance().clear();
      StateDatabase.getInstance().clear();
      initialTape.reset();
      initialMachineState = null;
      return true;
    }
    return false;
  }

  private boolean saveMachineToFile(File f) {
    Symbol[] symbols = SymbolDatabase.getInstance().values();
    State[] states = StateDatabase.getInstance().values();
    try (
      DataOutputStream dos = new DataOutputStream(new FileOutputStream(f))
    ) {
      dos.writeInt(symbols.length);
      dos.writeInt(states.length);
      for (Symbol s : symbols) {
        dos.writeUTF(s.toString());
      }
      for (State s : states) {
        dos.writeUTF(s.toString());
      }
      initialTape.save(dos, symbols);
      for (int i = 0; i < symbols.length; i++) {
        for (int j = 0; j < states.length; j++) {
          Transition.Right t = TransitionTableModel.getInstance()
            .getTransition(symbols[i], states[j]);
          dos.writeInt(Utils.indexOf(states, t.getState()));
          dos.writeInt(t.getMachineAction().ordinal());
          dos.writeInt(Utils.indexOf(symbols, t.getSymbol()));
        }
      }
    } catch (Exception e) {
      System.err.println("Error while saving the file :");
      e.printStackTrace();
      return true;
    }
    return false;
  }

}
