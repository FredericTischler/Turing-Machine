package org.noopi.view.swing.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.noopi.model.database.IDatabase;
import org.noopi.utils.events.view.ElementAddedEvent;
import org.noopi.utils.events.view.ElementRemovedEvent;
import org.noopi.utils.listeners.view.ElementAddedEventListener;
import org.noopi.utils.listeners.view.ElementRemovedEventListener;
import org.noopi.view.swing.components.model.DatabaseListModel;

public class DatabaseList<T> extends JPanel {

  private static final int FIELD_DISPLAYABLE_WIDTH = 15;

  private HintableTextField field;
  private JButton addButton;
  private JButton removeButton;
  
  private DatabaseListModel<T> model;
  private JList<String> list;

  private EventListenerList listenerList;
  private ElementAddedEvent addEvent;
  private ElementRemovedEvent removeEvent;

  public DatabaseList(
    String hint,
    String addButtonText,
    String removeButtonText,
    IDatabase<String, T> database
  ) {
    assert hint != null;
    assert addButtonText != null;
    assert removeButtonText != null;
    assert database != null;

    field = new HintableTextField("", hint, FIELD_DISPLAYABLE_WIDTH);
    addButton = new JButton(addButtonText);
    removeButton = new JButton(removeButtonText);
    model = new DatabaseListModel<>(database);
    list = new JList<>(model);
    listenerList = new EventListenerList();
    
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    JPanel header = new JPanel();
    header.add(field);
    header.add(addButton);
    header.add(removeButton);

    add(header);
    add(new JScrollPane(list));

    // FIX: if too quick, text field does not update
    list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
          return;
        }
        field.setText(model.getElementAt(e.getFirstIndex()));
      }
    });

    field.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == '\n'){
          fireElementAddedEvent(field.getText());
        }
      }
    });

    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        fireElementAddedEvent(field.getText());
      }
    });

    removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        fireElementRemovedEvent(field.getText());
      }
    });
  }

  public void setActive(boolean active){
    addButton.setEnabled(active);
    removeButton.setEnabled(active);
    field.setEditable(active);
  }

  public void addElementAddedEventListener(ElementAddedEventListener l) {
    assert l != null;
    listenerList.add(ElementAddedEventListener.class, l);
  }

  public void addElementRemovedEventListener(ElementRemovedEventListener l) {
    assert l != null;
    listenerList.add(ElementRemovedEventListener.class, l);
  }

  protected void fireElementAddedEvent(String s) {
    Object[] list = listenerList.getListenerList();
    boolean b = false;
    for (int i = list.length - 2; i >= 0; i -= 2) {
      if (list[i] != ElementAddedEventListener.class) {
        continue;
      }
      if (addEvent == null || !b) {
        addEvent = new ElementAddedEvent(s);
      }
      ((ElementAddedEventListener) list[i + 1]).onElementAdded(addEvent);
    }
  }

  protected void fireElementRemovedEvent(String s) {
    Object[] list = listenerList.getListenerList();
    boolean b = false;
    for (int i = list.length - 2; i >= 0; i -= 2) {
      if (list[i] != ElementRemovedEventListener.class) {
        continue;
      }
      if (removeEvent == null || !b) {
        removeEvent = new ElementRemovedEvent(s);
        b = true;
      }
      ((ElementRemovedEventListener) list[i + 1]).onElementRemoved(removeEvent);
    }
  }
}
