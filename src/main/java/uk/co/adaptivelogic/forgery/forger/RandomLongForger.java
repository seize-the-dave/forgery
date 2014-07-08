package uk.co.adaptivelogic.forgery.forger;

import uk.co.adaptivelogic.forgery.Forger;

import java.util.Random;

public class RandomLongForger implements Forger<Long> {
	public Long forge() {
		return new Random().nextLong();
	}
}
