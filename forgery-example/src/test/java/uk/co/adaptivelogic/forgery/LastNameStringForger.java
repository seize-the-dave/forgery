package uk.co.adaptivelogic.forgery;

import javax.inject.Provider;
import java.util.Random;

@Property("lastName")
public class LastNameStringForger implements Provider<String> {

    private static final String[] LAST_NAMES = {"Farmer", "Smith", "Grant", "Jones", "Gunthorpe", "Griffiths"};

	public String get() {
        return LAST_NAMES[new Random().nextInt(LAST_NAMES.length)];
	}
}
