package uk.co.adaptivelogic.forgery;

public class Forgery {
  public <T> static forge(Class<T> type) {
    return type.newInstance();
  }
}
