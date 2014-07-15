package uk.co.adaptivelogic.forgery.forger;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import uk.co.adaptivelogic.forgery.AbstractForger;
import uk.co.adaptivelogic.forgery.Property;

@Property({"dateOfBirth", "birthDate"})
public class RandomDateOfBirthForger extends AbstractForger<Date> {
	public Date forge() {
		Random rnd = new Random();

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.roll(Calendar.YEAR, -18 - rnd.nextInt(81));
		calendar.roll(Calendar.MONTH, rnd.nextInt(11));
		calendar.roll(Calendar.DAY_OF_MONTH, rnd.nextInt(31));

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
}
