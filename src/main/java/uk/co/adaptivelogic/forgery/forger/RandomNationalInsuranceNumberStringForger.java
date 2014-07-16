package uk.co.adaptivelogic.forgery.forger;

import org.apache.commons.lang.RandomStringUtils;
import uk.co.adaptivelogic.forgery.Property;

import javax.inject.Provider;
import java.util.Random;

@Property({"niNumber", "nationalInsurance"})
public class RandomNationalInsuranceNumberStringForger implements Provider<String> {
	public String get() {
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
