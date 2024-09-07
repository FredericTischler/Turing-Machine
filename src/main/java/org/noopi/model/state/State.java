package org.noopi.model.state;

public final class State {

  public static final State DEFAULT = new State("");

  private final String name;

  protected State(String name) {
    assert name != null;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof State) && ((State) o).name.equals(name);
  }

  @Override
  public String toString() {
    return name;
  }
}
