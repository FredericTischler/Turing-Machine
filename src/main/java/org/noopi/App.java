package org.noopi;

import javax.swing.SwingUtilities;

import org.noopi.gui.Controller;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new Controller().display();
      }
    });
  }
}
