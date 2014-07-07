package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        T forgedType;

        try {
            forgedType = checkNotNull(type, MISSION_IMPOSSIBLE).newInstance();
            forgeProperties(type, forgedType);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }

        return forgedType;
    }

    private static <T> void forgeProperties(Class<T> type, T generatedType) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : properties) {
            forgeProperty(generatedType, property);
        }
    }

    private static <T> void forgeProperty(T type, PropertyDescriptor property) throws IllegalAccessException, InvocationTargetException {
        Method write = property.getWriteMethod();
        if (write != null) {
            write.invoke(type, forge(property.getPropertyType()));
        }
    }

    private static final String MISSION_IMPOSSIBLE = "Mission Impossible attempting to forge null classes :)";
}
