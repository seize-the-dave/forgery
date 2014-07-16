package uk.co.adaptivelogic.forgery;

import javax.inject.Provider;

@Property("lastName")
public class LastNameStringForger implements Provider<String> {
	public String get() {
		return "Smith";
	}
}
