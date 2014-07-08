package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.reflect.TypeToken;

import javax.annotation.Nonnull;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Entry point for the forgery of all domain objects.
 * <p/>
 * <p>
 * If you need to auto create a class object then pass it to Forgery and let us provide a properly constructed
 * class for your use.  All you need to do is <pre>Forgery.forge(ToForge.class)</pre>
 * </p>
 */
public class Forgery {
	private static final String MISSION_IMPOSSIBLE = "Mission Impossible attempting to forge null classes :)";
	private Map<Type, ForgerCollection<?>> forgerMap = new HashMap<Type, ForgerCollection<?>>();

	public Forgery(Forger<?>... forgers) {
		this();
		for (Forger<?> forger : forgers) {
			addToForgerMap(forger);
		}
	}

	public Forgery() {
		for (Forger<?> forger : ServiceLoader.load(Forger.class)) {
			addToForgerMap(forger);
		}
}

	private void addToForgerMap(Forger forger) {
		Type genericType = getGenericType(forger);
		if (!forgerMap.containsKey(genericType)) {
			forgerMap.put(genericType, new ForgerCollection());
		}
		forgerMap.get(genericType).add(forger);
	}

	private Type getGenericType(Forger forger) {
		return getGenericType((ParameterizedType) TypeToken.of(forger.getClass()).getSupertype(Forger.class).getType());
	}

	private Type getGenericType(ParameterizedType type) {
		return type.getActualTypeArguments()[0];
	}

	public <T> T forge(@Nonnull Type type) {
		T forgedType;

		try {
			type = checkNotNull(type, MISSION_IMPOSSIBLE);
			log("Forging " + type);
			if (forgerMap.containsKey(type)) {
				Optional<T> forged = (Optional<T>) forgerMap.get(type).forge();
				if (forged.isPresent()) {
					return forged.get();
				}
			} else {
				log("No Forger available for " + type);
			}
			forgedType = ((Class<T>) type).newInstance();
			forgeProperties(type, forgedType);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}

		return forgedType;
	}

	public <T> T forge(@Nonnull Type type, String property) {
		log("Forging " + property + " (" + type + ")");
		try {
			type = checkNotNull(type, MISSION_IMPOSSIBLE);
			if (forgerMap.containsKey(type)) {
				Optional<T> forged = (Optional<T>) forgerMap.get(type).forge(property);
				if (forged.isPresent()) {
					return forged.get();
				} else {
					log("No Forger available for " + property + " (" + type + ")");
				}
			} else {
				log("No Forger available for " + property + " (" + type + ")");
			}
			return forge(type);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	private <T> void forgeProperties(Type type, T forgedObject) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(forgedObject.getClass());
		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : properties) {
			forgeProperty(forgedObject, property);
		}
	}

	private <T> void forgeProperty(T forgedObject, PropertyDescriptor property) throws IllegalAccessException, InvocationTargetException {
		Method write = property.getWriteMethod();
		Method read = property.getReadMethod();
		if (write != null) {
			write.invoke(forgedObject, forge(TypeToken.of(read.getGenericReturnType()).getType(), property.getName()));
		}
	}

	private void log(String message) {
		System.out.println(message);
	}
}
