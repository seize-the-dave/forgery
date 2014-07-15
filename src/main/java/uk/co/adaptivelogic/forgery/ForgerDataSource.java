package uk.co.adaptivelogic.forgery;

import java.util.Locale;
import java.util.Set;

public interface ForgerDataSource<T> {
	Set<T> getValues(Locale locale);
	T getNextValue(Locale locale);
}
