package uk.co.adaptivelogic.forgery.forger;

import org.apache.commons.lang.RandomStringUtils;
import uk.co.adaptivelogic.forgery.Forger;
import uk.co.adaptivelogic.forgery.Property;

import java.util.Random;

@Property({"ssn", "socialSecurityNumber"})
public class RandomSocialSecurityNumberStringForger implements Forger<String> {
	public String forge() {
		return RandomStringUtils.random(9, false, true);
	}
}
