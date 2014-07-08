package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ForgerCollection<T> {
	public static final Pattern MATCH_NOTHING = Pattern.compile("^$");
	private static final Logger LOGGER = LoggerFactory.getLogger(Forgery.class);
	private Map<Pattern, Forger<T>> map = new HashMap<Pattern, Forger<T>>();

	public void add(Forger<T> forger) {
		Pattern pattern = patternFrom(forger);
		log("Registering " + forger + " with pattern '" + pattern + "'");
		map.put(pattern, forger);
	}

	private Pattern patternFrom(Forger<T> forger) {
		if (forger.getClass().getAnnotation(Property.class) == null) {
			return MATCH_NOTHING;
		} else {
			return Pattern.compile(forger.getClass().getAnnotation(Property.class).value());
		}
	}

	public Optional<T> forge(String property) {
		for (Map.Entry<Pattern, Forger<T>> entry : map.entrySet()) {
			Pattern pattern = entry.getKey();
			log("Attempting to match '" + property + "' using pattern '" + pattern + "'");
			if (pattern.matcher(property).matches()) {
				log("'" + property + "' was matched by pattern '" + pattern + "'");
				log("Using " + entry.getValue() + " to forge property '" + property + "'");
				return Optional.of(entry.getValue().forge());
			} else {
				log("'" + property + "' was not matched by pattern '" + pattern + "'");
			}
		}
		return forge();
	}

	public Optional<T> forge() {
		if (map.containsKey(MATCH_NOTHING)) {
			log("Using " + map.get(MATCH_NOTHING));
			return Optional.of(map.get(MATCH_NOTHING).forge());
		} else {
			return Optional.absent();
		}
	}

	private void log(String message) {
		LOGGER.info(message);
	}
}
