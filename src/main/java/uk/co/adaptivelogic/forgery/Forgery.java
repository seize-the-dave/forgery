package uk.co.adaptivelogic.forgery;

import com.google.common.base.Throwables;

public class Forgery {

  public static <T> T forge(Class<T> type) {
      T generatedType;

      try {
           generatedType = type.newInstance();
      } catch (Exception e) {
          throw Throwables.propagate(e);
      }

      return generatedType;
  }

}
