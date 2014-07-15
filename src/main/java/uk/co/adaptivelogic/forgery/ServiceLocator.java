package uk.co.adaptivelogic.forgery;

import java.util.Properties;

import com.google.common.base.Optional;

public interface ServiceLocator {
	void registerDataSources(Properties properties);
	<T> void registerDataSource(String name, ForgerDataSource<T> dataSource);
	<T> Optional<ForgerDataSource<T>> lookupDataSource(String name) throws ServiceException;
}
