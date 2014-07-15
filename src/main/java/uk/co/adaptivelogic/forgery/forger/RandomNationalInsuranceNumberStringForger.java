package uk.co.adaptivelogic.forgery.forger;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

import uk.co.adaptivelogic.forgery.AbstractForger;
import uk.co.adaptivelogic.forgery.Property;

@Property({"niNumber", "nationalInsurance"})
public class RandomNationalInsuranceNumberStringForger extends AbstractForger<String> {
	public String forge() {
		StringBuilder builder = new StringBuilder();
		builder.append(RandomStringUtils.randomAlphabetic(2).toUpperCase());
		builder.append(" ");
		builder.append(10 + new Random().nextInt(89));
		builder.append(" ");
		builder.append(10 + new Random().nextInt(89));
		builder.append(" ");
		builder.append(10 + new Random().nextInt(89));
		builder.append(" ");
		builder.append(RandomStringUtils.random(1, "ABCD").toUpperCase());

		return builder.toString();
	}
}
