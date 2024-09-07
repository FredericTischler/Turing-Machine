package org.noopi.view.swing.menubar;

import java.util.EnumMap;
import java.util.Map;

public enum Menu {
  FILE("Fichier"),
  EDIT("Aide");
  
  private String label;

  Menu(String lb) {
    label = lb;
  }

  public String label() {
    return label;
  }

  public static final Map<Menu, Item[]> STRUCTURE;

  static {
    STRUCTURE = new EnumMap<Menu, Item[]>(Menu.class);
    STRUCTURE.put(
      Menu.FILE,
      new Item[] {
        Item.NEW,
        Item.OPEN,
        null,
        Item.SAVE,
        Item.SAVE_AS
      }
    );
  }
}
