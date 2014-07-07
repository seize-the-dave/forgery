package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static <T> T forge(@Nonnull Class<T> type) {
        T generatedType;

        try {
            generatedType = checkNotNull(type, MISSION_IMPOSSIBLE).newInstance();
            if (type.equals(String.class)) {
                return generatedType;
            }
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }

        return generatedType;
    } 
    
    private static final String MISSION_IMPOSSIBLE = "Mission Impossible attempting to forge null classes :)";
}
