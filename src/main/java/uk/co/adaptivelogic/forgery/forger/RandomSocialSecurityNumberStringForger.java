package uk.co.adaptivelogic.forgery.forger;

import org.apache.commons.lang.RandomStringUtils;

import uk.co.adaptivelogic.forgery.AbstractForger;
import uk.co.adaptivelogic.forgery.Property;

@Property({"ssn", "socialSecurityNumber"})
public class RandomSocialSecurityNumberStringForger extends AbstractForger<String> {
	public String forge() {
		return RandomStringUtils.random(9, false, true);
	}
}
