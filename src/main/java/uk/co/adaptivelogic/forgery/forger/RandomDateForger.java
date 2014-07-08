package uk.co.adaptivelogic.forgery.forger;

import uk.co.adaptivelogic.forgery.Forger;

import java.util.Date;
import java.util.Random;

public class RandomDateForger implements Forger<Date> {
	public Date forge() {
		Date date = new Date();
		date.setTime(new Random().nextLong());

		return date;
	}
}
