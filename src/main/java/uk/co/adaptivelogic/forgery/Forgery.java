package uk.co.adaptivelogic.forgery;

public class Forgery {
  public <T> static T forge(Class<T> type) {
    return type.newInstance();
  }
}
