package uk.co.adaptivelogic.forgery.forger;

import java.util.Date;
import java.util.Random;

import uk.co.adaptivelogic.forgery.AbstractForger;

public class RandomDateForger extends AbstractForger<Date> {
	public Date forge() {
		Date date = new Date();
		date.setTime(new Random().nextLong());

		return date;
	}
}
