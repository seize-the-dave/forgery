package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

public class ForgerCollection<T> {
	public static final Pattern MATCH_NOTHING = Pattern.compile("^$");
	private static final Logger LOGGER = LoggerFactory.getLogger(Forgery.class);
	private Map<Pattern, Forger<T>> map = new HashMap<Pattern, Forger<T>>();
	private Map<String, Forger<T>> cacheMap = new WeakHashMap<String, Forger<T>>();

	public void add(Forger<T> forger) {
		for (Pattern pattern : patternsFrom(forger)) {
			log("Registering " + forger + " with pattern '" + pattern + "'");
			map.put(pattern, forger);
		}
	}

	private List<Pattern> patternsFrom(Forger<T> forger) {
		if (forger.getClass().getAnnotation(Property.class) == null) {
			return Collections.singletonList(MATCH_NOTHING);
		} else {
			List<Pattern> patterns = new ArrayList<Pattern>();
			for (String pattern : forger.getClass().getAnnotation(Property.class).value()) {
				patterns.add(Pattern.compile(pattern));
			}

			return patterns;
		}
	}

	public Optional<T> forge(String property) {
		if (cacheMap.containsKey(property)) {
			log("Hit cache for property '" + property + "'");
			return Optional.of(cacheMap.get(property).forge());
		} else {
			log("Missed cache for property '" + property + "'");
		}
		for (Map.Entry<Pattern, Forger<T>> entry : map.entrySet()) {
			Pattern pattern = entry.getKey();
			log("Attempting to match '" + property + "' using pattern '" + pattern + "'");
			if (pattern.matcher(property).matches()) {
				log("'" + property + "' was matched by pattern '" + pattern + "'");
				log("Using " + entry.getValue() + " to forge property '" + property + "'");
				cacheMap.put(property, entry.getValue());
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
