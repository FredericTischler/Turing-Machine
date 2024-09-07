package org.noopi.view.swing.components.model;

import javax.swing.table.AbstractTableModel;

import org.noopi.model.database.IDatabase;
import org.noopi.model.state.State;
import org.noopi.model.state.StateDatabase;
import org.noopi.model.symbol.Symbol;
import org.noopi.model.symbol.SymbolDatabase;
import org.noopi.model.transition.Transition;
import org.noopi.utils.listeners.TransitionTableUpdatedEventListener;

public class TransitionTableModel extends AbstractTableModel {

  private static TransitionTableModel INSTANCE;

  private TransitionTableModel(
  ) {
    org.noopi.model.transition.TransitionTableModel.getInstance()
    .addTableUpdatedEventListener(
      new TransitionTableUpdatedEventListener() {
        @Override
        public void onTableUpdated() {
          fireTableStructureChanged();
        }      
      }
    );
  }

  @Override
  public int getRowCount() {
    return SymbolDatabase.getInstance().size();
  }

  @Override
  public int getColumnCount() {
    return StateDatabase.getInstance().size() + 1;
  }

  @Override
  public String getColumnName(int columnIndex) {
    return columnIndex == 0
      ? "Symboles"
      : StateDatabase.getInstance().values()[columnIndex - 1].toString();
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return String.class;
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    final IDatabase<String, Symbol> symbols = SymbolDatabase.getInstance();
    final IDatabase<String, State> states = StateDatabase.getInstance();
    final org.noopi.model.transition.TransitionTableModel transitions =
      org.noopi.model.transition.TransitionTableModel.getInstance();

    if (columnIndex == 0) {
      return symbols.entries()[rowIndex];
    }
    Transition.Right v = transitions.getTransition(
      new Transition.Left(
        symbols.values()[rowIndex],
        states.values()[columnIndex - 1]
      )
    );
    return v.getSymbol() + ", " + v.getMachineAction() + ", " + v.getState();
  }
  
  public static final TransitionTableModel getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new TransitionTableModel();
    }
    return INSTANCE;
  }
}
