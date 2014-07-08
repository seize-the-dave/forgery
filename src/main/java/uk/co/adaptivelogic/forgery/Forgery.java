package uk.co.adaptivelogic.forgery;

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
	public static final Pattern MATCH_NOTHING = Pattern.compile("^$");
	private Map<Type, Map<Pattern, Forger<?>>> forgerMap = new HashMap<Type, Map<Pattern, Forger<?>>>();

	public Forgery(Forger... forgers) {
		this();
		for (Forger forger : forgers) {
			addToForgerMap(forger);
		}
	}

	public Forgery() {
		for (Forger forger : ServiceLoader.load(Forger.class)) {
			addToForgerMap(forger);
		}
	}

	private void addToForgerMap(Forger forger) {
		Pattern pattern;
		if (forger.getClass().getAnnotation(Property.class) == null) {
			pattern = MATCH_NOTHING;
		} else {
			pattern = Pattern.compile(forger.getClass().getAnnotation(Property.class).value());
		}
		Type genericType = getGenericType(forger);
		if (!forgerMap.containsKey(genericType)) {
			Map<Pattern, Forger<?>> patternMap = new HashMap<Pattern, Forger<?>>();
			forgerMap.put(genericType, patternMap);
		}
		forgerMap.get(genericType).put(pattern, forger);
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
				return (T) forgerMap.get(type).get(MATCH_NOTHING).forge();
			}
			forgedType = type.newInstance();
			forgeProperties(type, forgedType);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}

		return forgedType;
	}

	public <T> T forge(@Nonnull Class<T> type, String property) {
		try {
			type = checkNotNull(type, MISSION_IMPOSSIBLE);
			if (forgerMap.containsKey(type)) {
				Map<Pattern, Forger<?>> patternMap = forgerMap.get(type);
				for (Map.Entry<Pattern, Forger<?>> entry : patternMap.entrySet()) {
					if (entry.getKey().matcher(property).matches()) {
						return (T) entry.getValue().forge();
					}
				}
			}
			return forge(type);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
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
			write.invoke(type, forge(property.getPropertyType(), property.getName()));
		}
	}
}
