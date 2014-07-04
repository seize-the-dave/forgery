package uk.co.adaptivelogic.forgery;

import com.google.common.base.Throwables;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Entry point for the forgery of all domain objects.
 *
 * <p>
 *     If you need to auto create a class object then pass it to Forgery and let us provide a properly constructed
 *     class for your use.  All you need to do is <pre>Forgery.forge(ToForge.class)</pre>
 * </p>
 */
public class Forgery {

    private static final String MISSION_IMPOSSIBLE = "Mission Impossible attempting to forge null classes :)";

    public static <T> T forge(@Nonnull Class<T> type) {

      T generatedType;

      try {
           generatedType = checkNotNull(type, MISSION_IMPOSSIBLE).newInstance();
      } catch (Exception e) {
          throw Throwables.propagate(e);
      }

      return generatedType;
  }

}
