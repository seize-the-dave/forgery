package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

public class ForgerCollection<T> {
	public static final Pattern MATCH_NOTHING = Pattern.compile("^$");
	private static final Logger LOGGER = LoggerFactory.getLogger(ForgerCollection.class);
	private Map<Pattern, Forger<T>> map = new HashMap<Pattern, Forger<T>>();

	public void add(Forger<T> forger) {
		for (Pattern pattern : patternsFrom(forger)) {
			LOGGER.info("Registering " + forger.getClass() + " for property pattern '" + pattern + "'");

			map.put(pattern, forger);
		}
	}

	private List<Pattern> patternsFrom(Forger<T> forger) {
		List<Pattern> patterns = new ArrayList<Pattern>();
		for (String pattern : forger.getClass().getAnnotation(Property.class).value()) {
			patterns.add(Pattern.compile(pattern));
		}

		return patterns;
	}

	public Optional<Forger<T>> lookup(String property) {
		LOGGER.info("Looking up Forger for property '" + property + "'");
		for (Map.Entry<Pattern, Forger<T>> entry : map.entrySet()) {
			Pattern pattern = entry.getKey();
			if (pattern.matcher(property).matches()) {
				LOGGER.info("Forger found for property '" + property + "'");
				return Optional.of(entry.getValue());
			}
		}
		LOGGER.warn("Forger not found for property '" + property + "'");
		return Optional.absent();
	}
}
