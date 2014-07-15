package uk.co.adaptivelogic.forgery;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;

public class InMemoryServiceLocator implements ServiceLocator {
	private Map<String, ForgerDataSource<?>> dataSourceMap = new HashMap<String, ForgerDataSource<?>>();
	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryServiceLocator.class);
	private static final int FIRST_PARAMETER = 0;

	private Type getParameterType(ForgerDataSource<?> forger) {
		Type forgerType = TypeToken.of(forger.getClass()).getSupertype(ForgerDataSource.class).getType();
		ParameterizedType parameterizedType = ParameterizedType.class.cast(forgerType);

		return parameterizedType.getActualTypeArguments()[FIRST_PARAMETER];
	}

	@Override
	public <T> Optional<ForgerDataSource<T>> lookupDataSource(String name) {
		LOGGER.info("Looking up DataSource for {}", name);
		if (dataSourceMap.containsKey(name)) {
			LOGGER.info("DataSource found for {}", name);
			return Optional.of((ForgerDataSource<T>) dataSourceMap.get(name));
		} else {
			LOGGER.warn("DataSource not found for {}", name);
			return Optional.absent();
		}
	}

	@Override
	public <T> void registerDataSource(String name, ForgerDataSource<T> dataSource) {
		LOGGER.info("Registering data source named {} as a data source of {}", name, getParameterType(dataSource));
		dataSourceMap.put(name, dataSource);
	}

	@Override
	public void registerDataSources(@Nonnull Properties properties) {

		for (final String name : properties.stringPropertyNames()) {
			String className = properties.getProperty(name);

			ForgerDataSource<?> dataSource = create(ForgerDataSource.class, className);

			LOGGER.info("Registering data source named {} as a data source of {}", name, getParameterType(dataSource));
			dataSourceMap.put(name, dataSource);
		}
	}

	private static <T> T create(final Class<T> superType, final String className) {
		try {
			final Class<?> clazz = Class.forName(className);
			final Object object = clazz.newInstance();
			if (superType.isInstance(object)) {
				return (T) object; // safe cast
			}
			return null; // or other error 
		} catch (Exception e) {
			return null;
		}
	}
}
