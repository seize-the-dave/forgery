package uk.co.adaptivelogic.forgery;

public class Forgery {
  public static <T> T forge(Class<T> type) {
    return type.newInstance();
  }
}
