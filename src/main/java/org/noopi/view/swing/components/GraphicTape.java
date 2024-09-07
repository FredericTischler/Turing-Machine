package org.noopi.view.swing.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.noopi.model.symbol.Symbol;
import org.noopi.model.tape.ITape;
import org.noopi.utils.events.tape.TapeMovedEvent;
import org.noopi.utils.listeners.tape.TapeMovedEventListener;
import org.noopi.utils.listeners.view.TapeUpdatedEventListener;

public class GraphicTape extends JList<String> {

  public static final int CELL_COUNT = 9;

  private static final int START_INDEX = 0;
  private static final int END_INDEX = CELL_COUNT - 1;
  private static final Color COLOR_TAB[] = {Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY};
  private static final Color SELECTED_COLOR = Color.BLUE;
  private static final Color SELECTED_COLOR_FOREGROUND = Color.BLACK;

  private ITape model;

  private int offset;

  // ATTRIBUTS
  private DefaultListModel<String> list;
  private final boolean selectable;

  // CONSTRUCTEUR

  public GraphicTape(ITape tape, boolean selectable, int cellSize){
    this(tape, selectable, cellSize, cellSize);
  }

  public GraphicTape(ITape tape, boolean selectable, int cellWidth, int cellHeight) {
    assert tape != null;
    this.model = tape;
    this.selectable = selectable;

    list = new DefaultListModel<>();
    Symbol[] symbols = tape.getSlice((CELL_COUNT - 1) / 2);
    for (int i = 0; i < CELL_COUNT; ++i) {
      list.add(i, symbols[i].toString());
    }
    setModel(list);
    setCellRenderer(new TapeCellRenderer(cellWidth, cellHeight));

    setEnabled(selectable);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setSelectedIndex(CELL_COUNT / 2);
    setVisibleRowCount(1);
    setLayoutOrientation(JList.HORIZONTAL_WRAP);

    tape.addTapeUpdatedEventListener(new TapeUpdatedEventListener() {
      @Override
      public void onUpdate() {
        list.removeAllElements();
        Symbol[] symbols = model.getSlice((CELL_COUNT - 1) / 2);
        for (int i = END_INDEX; i >= START_INDEX; --i) {
          list.add(0, symbols[i].toString());
        }
      }
    });

    tape.addTapeMovedEventListener(new TapeMovedEventListener() {
      @Override
      public void onTapeMoved(TapeMovedEvent e) {
        switch (e.getDirection()) {
          case TAPE_LEFT: --offset; break;
          case TAPE_RIGHT: ++offset; break;
          default: return;
        }
        offset %= COLOR_TAB.length;
      }
    });

    offset = 0;
  }

  // COMMANDES

  @Override
  public void paintComponent(Graphics g) {
    int w = (getWidth() / CELL_COUNT);
    int h = getHeight();
    w = h = Math.min(w, h);
    setFixedCellWidth(w);
    setFixedCellHeight(h);
    setPreferredSize(new Dimension(CELL_COUNT * w, h));
    setSize(CELL_COUNT * w, h);
    super.paintComponent(g);
  }

  // TYPE IMBRIQUE

  private class TapeCellRenderer
    extends JLabel
    implements ListCellRenderer<String>
  {

    public TapeCellRenderer(int cellW, int cellH) {
      setOpaque(true);
      setPreferredSize(new Dimension(cellW, cellH));
      setHorizontalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(
      JList<? extends String> list,
      String value,
      int index,
      boolean isSelected,
      boolean cellHasFocus
    ) {
      setText(value);
      setBorder(BorderFactory.createLineBorder(Color.BLACK));
      setBackground(COLOR_TAB[(COLOR_TAB.length + index + offset) % COLOR_TAB.length]);
      if(selectable && isSelected){
        setBackground(SELECTED_COLOR);
        setForeground(SELECTED_COLOR_FOREGROUND);
      }
      return this;
    }
  }
}

