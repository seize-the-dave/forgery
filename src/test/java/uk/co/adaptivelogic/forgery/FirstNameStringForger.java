package uk.co.adaptivelogic.forgery;

import javax.inject.Provider;

@Property("firstName")
public class FirstNameStringForger implements Provider<String> {
	public String get() {
		return "John";
	}
}
