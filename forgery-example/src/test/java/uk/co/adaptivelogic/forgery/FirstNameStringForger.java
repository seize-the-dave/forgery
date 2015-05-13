package uk.co.adaptivelogic.forgery;

import javax.inject.Provider;
import java.util.Random;

@Property("firstName")
public class FirstNameStringForger implements Provider<String> {

    private static final String[] FIRST_NAMES = {"Sarah", "David", "Rebecca", "Lea", "Lisa", "Steve"};

	public String get() {
		return FIRST_NAMES[new Random().nextInt(FIRST_NAMES.length)];
	}
}
