package org.noopi.utils.events.view;

public class SpeedChangeEvent {
  // ATTRIBUTS

  private double speed;

  // CONSTRUCTEURS

  public SpeedChangeEvent(double s) {
    speed = s;
  }

  // REQUETES

  public double getSpeed() {
    return speed;
  }
}
