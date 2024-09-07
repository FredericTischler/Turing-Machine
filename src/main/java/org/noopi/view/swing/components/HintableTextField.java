package org.noopi.view.swing.components;

import javax.swing.JTextField;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.Color;

public class HintableTextField extends JTextField {
  private String hint;
  private boolean hinted;

  public HintableTextField() {
    this("", "", 0);
  }

  public HintableTextField(int columns) {
    this("", "", columns);
  }

  public HintableTextField(String text) {
    this(text, "", 0);
  }

  public HintableTextField(String text, int columns) {
    this(text, "", columns);
  }

  public HintableTextField(String text, String hint) {
    this(text, "", 0);
  }

  public HintableTextField(String text, String hint, int columns) {
    super(text, columns);
    this.hint = hint;
    hint();
    
    addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        restore();
      }
      @Override
      public void focusLost(FocusEvent e) {
        hint();
      }
    });
  }

  public void setHint(String hint) {
    this.hint = hint == null
      ? ""
      : hint;
  }

  public String getHint() {
    return hint;
  }

  @Override
  public String getText() {
    return hinted
      ? ""
      : super.getText();
  }

  private void restore() {
    if (hinted) {
      setText("");
      setForeground(Color.BLACK);
    }
  }

  private void hint() {
    if (hinted = super.getText().isEmpty()) {
      setForeground(Color.GRAY);
      setText(hint);
    }
  }
}
