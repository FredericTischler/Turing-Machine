package org.noopi.model.tape;

import java.io.DataOutputStream;
import java.io.IOException;

import org.noopi.model.machine.MachineAction;
import org.noopi.model.symbol.Symbol;
import org.noopi.utils.Utils;

public final class Tape extends AbstractTape {

  private Cell currentCell;

  private int cellsToTheRight;
  private int cellsToTheLeft;

  public Tape() {
    reset();
  }

  @Override
  public void reset() {
    reset(new Symbol[]{Symbol.DEFAULT});
  }

  @Override
  public void reset(Symbol[] symbols) {
    assert symbols != null;
    assert symbols.length > 0;
    cellsToTheLeft = 0;
    cellsToTheRight = symbols.length - 1;
    for (Symbol s : symbols) {
      assert s != null;
    }
    final Cell orig = new Cell();
    currentCell = orig;
    for (Symbol s : symbols) {
      currentCell.symbol = s;
      currentCell.next = new Cell();
      currentCell.next.prev = currentCell;
      currentCell = currentCell.next;
    }
    currentCell = orig;
    fireTapeUpdatedEvent();
  }

  @Override
  public void shift(MachineAction d) {
    assert d != null;
    switch (d) {
      case TAPE_LEFT:
        currentCell = currentCell.getPrev();
        if (cellsToTheLeft > 0) {
          --cellsToTheLeft;
        }
        ++cellsToTheRight;
      break;
      case TAPE_RIGHT:
        currentCell = currentCell.getNext();
        if (cellsToTheRight > 0) {
          --cellsToTheRight;
        }
        ++cellsToTheLeft;
      break;
      case MACHINE_STOP: return; // TODO: throw exception maybe ?
    }
    fireTapeMovedEvent(d);
    fireTapeUpdatedEvent();
  }

  @Override
  public Symbol readSymbol() {
    return currentCell.symbol;
  }

  @Override
  public void writeSymbol(Symbol symbol) {
    assert symbol != null;
    currentCell.symbol = symbol;
    fireTapeWriteEvent(symbol);
    fireTapeUpdatedEvent();
  }

  @Override
  public Symbol[] getSlice(int spanWidth) {
    assert spanWidth >= 0;
    Symbol[] span = new Symbol[2 * spanWidth + 1];
    Cell cl = currentCell;
    Cell cg = currentCell;
    Symbol sl;
    Symbol sg;
    for (int i = 0; i < spanWidth; ++i) {
      if (cl.prev != null) {
        cl = cl.prev;
        sl = cl.symbol;
      } else {
        sl = Symbol.DEFAULT;
      }
      if (cg.next != null) {
        cg = cg.next;
        sg = cg.symbol;
      } else {
        sg = Symbol.DEFAULT;
      }
      span[spanWidth - i - 1] = sl;
      span[spanWidth + i + 1] = sg;
    }
    span[spanWidth] = currentCell.symbol;
    return span;
  }

  @Override
  public void save(DataOutputStream dos, Symbol[] symbols) throws IOException {
    Cell o = currentCell;
    for (int i = 0; i < cellsToTheLeft; ++i) {
      o = o.prev;
    }
    dos.writeInt(cellsToTheLeft + cellsToTheRight + 1);
    dos.writeInt(cellsToTheRight + 1);
    for (int i = 0; i <= cellsToTheLeft + cellsToTheRight; ++i) {
      dos.writeInt(Utils.indexOf(symbols, o.symbol));
      o = o.next;
    }
  }

  public void from(ITape o) {
    assert o != null : "Use reset() instead.";
    Tape oo = (Tape) o;

    currentCell = new Cell(oo.currentCell.symbol);
    cellsToTheLeft = oo.cellsToTheLeft;
    cellsToTheLeft = oo.cellsToTheRight;

    Cell current = currentCell;
    Cell oCurrent = oo.currentCell;
    for (int i = 0; i < oo.cellsToTheLeft; ++i) {
      current.prev = new Cell(oCurrent.prev.symbol);
      current.prev.next = current;
      current = current.prev;
      oCurrent = oCurrent.prev;
    }

    current = currentCell;
    oCurrent = oo.currentCell;
    for (int i = 0; i < oo.cellsToTheRight; ++i) {
      current.next = new Cell(oCurrent.next.symbol);
      current.next.prev = current;
      current = current.next;
      oCurrent = oCurrent.next;
    }

    fireTapeUpdatedEvent();
  }

  @Override
  public void removeAllOccurencesOfSymbol(Symbol s) {
    assert s != null;
    boolean update = false;
    if (currentCell.symbol.equals(s)) {
      currentCell.symbol = Symbol.DEFAULT;
      update = true;
    }
    Cell c = currentCell;
    for (int i = 1; i < cellsToTheLeft; ++i) {
      c = c.prev;
      if (c.symbol.equals(s)) {
        c.symbol = Symbol.DEFAULT;
        update = true;
      }
    }
    c = currentCell;
    for (int i = 1; i < cellsToTheRight; ++i) {
      c = c.next;
      if (c.symbol.equals(s)) {
        c.symbol = Symbol.DEFAULT;
        update = true;
      }
    }
    if (update) {
      fireTapeUpdatedEvent();
    }
  }

  private class Cell {
    private Cell next;
    private Cell prev;
    private Symbol symbol;

    public Cell() {
      this(Symbol.DEFAULT);
    }

    public Cell(Symbol s) {
      setSymbol(s);
    }

    public void setSymbol(Symbol s) {
      assert s != null;
      symbol = s;
    }

    public Cell getNext() {
      if (next == null) {
        next = new Cell();
        next.prev = this;
      }
      return next;
    }

    public Cell getPrev() {
      if (prev == null) {
        prev = new Cell();
        prev.next = this;
      }
      return prev;
    }
  }
}
