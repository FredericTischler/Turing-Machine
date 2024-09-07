package org.noopi.utils;

public class Utils {
  public static <T> int indexOf(T[] array, T element) {
    assert array != null;
    assert element != null;
    for (int i = 0; i < array.length; ++i) {
      if (element.equals(array[i])) return i;
    }
    return -1;
  }
}
