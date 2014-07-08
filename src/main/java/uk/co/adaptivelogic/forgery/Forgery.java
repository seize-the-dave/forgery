package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.base.Verify;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.omg.Dynamic.Parameter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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
    private Map<Type, Forger<?>> forgerMap = new HashMap<Type, Forger<?>>();
    
    public Forgery(Forger forger) {
		addToForgerMap(forger);
	}

	private void addToForgerMap(Forger forger) {
		forgerMap.put(getGenericType(forger), forger);
	}

	public Forgery() {
		for (Forger forger : ServiceLoader.load(Forger.class)) {
			addToForgerMap(forger);
		}
	}
    
    private Type getGenericType(Forger forger) {
		return getGenericType((ParameterizedType) TypeToken.of(forger.getClass()).getSupertype(Forger.class).getType());
    }
    
    private Type getGenericType(ParameterizedType type) {
        return type.getActualTypeArguments()[0];
    }

    public <T> T forge(@Nonnull Class<T> type) {
        T forgedType;

        try {
			type = checkNotNull(type, MISSION_IMPOSSIBLE);
			if (forgerMap.containsKey(type)) {
                return (T) forgerMap.get(type).forge();
            } else {
				forgedType = type.newInstance();
				forgeProperties(type, forgedType);
			}
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }

        return forgedType;
    }

    private <T> void forgeProperties(Class<T> type, T generatedType) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : properties) {
            forgeProperty(generatedType, property);
        }
    }

    private <T> void forgeProperty(T type, PropertyDescriptor property) throws IllegalAccessException, InvocationTargetException {
        Method write = property.getWriteMethod();
        if (write != null) {
            write.invoke(type, forge(property.getPropertyType()));
        }
    }

    private static final String MISSION_IMPOSSIBLE = "Mission Impossible attempting to forge null classes :)";
}
