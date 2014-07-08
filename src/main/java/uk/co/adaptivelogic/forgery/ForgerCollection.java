package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ForgerCollection<T> {
	public static final Pattern MATCH_NOTHING = Pattern.compile("^$");
	private Map<Pattern, Forger<T>> map = new HashMap<Pattern, Forger<T>>();

	public void add(Forger<T> forger) {
		map.put(patternFrom(forger), forger);
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
			if (entry.getKey().matcher(property).matches()) {
				return Optional.of(entry.getValue().forge());
			}
		}
		return forge();
	}

	public Optional<T> forge() {
		if (map.containsKey(MATCH_NOTHING)) {
			return Optional.of(map.get(MATCH_NOTHING).forge());
		} else {
			return Optional.absent();
		}
	}
}
