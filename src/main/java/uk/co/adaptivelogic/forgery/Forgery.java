package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(Forgery.class);
	private Map<Type, ForgerCollection<?>> forgerMap = new HashMap<Type, ForgerCollection<?>>();

	private Forgery(Iterable<Forger<?>> forgers) {
		for (Forger<?> forger : forgers) {
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
			log("Attempting to forge " + type);
			if (forgerMap.containsKey(type)) {
				Optional<T> forged = (Optional<T>) forgerMap.get(type).forge();
				if (forged.isPresent()) {
					return forged.get();
				}
			}
			log("No forger found for " + type);
			log("Creating a new instance of " + type + " using default constructor");
			forgedType = ((Class<T>) type).newInstance();
			log("Forging properties for " + type);
			forgeProperties(type, forgedType);
			log("Finished forging " + type);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}

		return forgedType;
	}

	public <T> T forge(@Nonnull Type type, String property) {
		log("Attempting to forge " + type + " for property '" + property + "'");
		try {
			type = checkNotNull(type, MISSION_IMPOSSIBLE);
			if (forgerMap.containsKey(type)) {
				Optional<T> forged = (Optional<T>) forgerMap.get(type).forge(property);
				if (forged.isPresent()) {
					return forged.get();
				}
			}
			log("No forger found for " + type + " for '" + property + "'; attempt to forge type instead");
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
			T value = forge(TypeToken.of(read.getGenericReturnType()).getType(), property.getName());
			log("Setting '" + property.getName() + "' to '" + value + "'");
			write.invoke(forgedObject, value);
		}
	}

	private void log(String message) {
		LOGGER.info(message);
	}

	public static class Builder {
		private List<Forger<?>> forgers = new ArrayList<Forger<?>>();

		public Builder() {
			for (Forger<?> forger : ServiceLoader.load(Forger.class)) {
				forgers.add(forger);
			}
		}

		public Builder withForger(Forger<?> forger) {
			forgers.add(forger);
			return this;
		}

		public Forgery build() {
			return new Forgery(forgers);
		}
	}
}
