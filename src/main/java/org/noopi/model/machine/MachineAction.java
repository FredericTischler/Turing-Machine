package org.noopi.model.machine;

public enum MachineAction {
  TAPE_RIGHT("left"),
  TAPE_LEFT("right"),
  MACHINE_STOP("stop");

  private final String r;

  MachineAction(String r) {
    this.r = r;
  }

  @Override
  public String toString() {
    return r;
  }
}
