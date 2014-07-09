package uk.co.adaptivelogic.forgery;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForgerResourceBundle implements ForgerDataSource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ForgerResourceBundle.class);
	
	@Override
	public Set<String> getValues(Class clazz, Locale locale) {
		String name = clazz.getSimpleName();
		LOGGER.debug("trying to find resource bundle named: {} for locale: {}", name ,locale);
		
		ResourceBundle bundle = ResourceBundle.getBundle(name, locale);
		
		Set<String> values = new HashSet<String>(Collections.list(bundle.getKeys()));
		LOGGER.debug("loaded resource bundle values: {} for locale: {}", values, locale);
		
		return values;
	}
}
