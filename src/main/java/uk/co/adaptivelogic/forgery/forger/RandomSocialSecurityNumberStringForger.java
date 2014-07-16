package uk.co.adaptivelogic.forgery.forger;

import org.apache.commons.lang.RandomStringUtils;
import uk.co.adaptivelogic.forgery.Property;

import javax.inject.Provider;

@Property({"ssn", "socialSecurityNumber"})
public class RandomSocialSecurityNumberStringForger implements Provider<String> {
	public String get() {
		return RandomStringUtils.random(9, false, true);
	}
}
