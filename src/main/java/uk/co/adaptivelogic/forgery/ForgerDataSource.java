package uk.co.adaptivelogic.forgery;

import java.util.Locale;
import java.util.Set;

public interface ForgerDataSource {
	<T> Set<String> getValues(Class clazz, Locale locale);
}
