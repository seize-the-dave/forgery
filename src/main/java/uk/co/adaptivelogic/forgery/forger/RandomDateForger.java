package uk.co.adaptivelogic.forgery.forger;

import javax.inject.Provider;
import java.util.Date;
import java.util.Random;

public class RandomDateForger implements Provider<Date> {
	public Date get() {
		Date date = new Date();
		date.setTime(new Random().nextLong());

		return date;
	}
}
