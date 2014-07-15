package uk.co.adaptivelogic.forgery.forger;

import java.util.Random;

import uk.co.adaptivelogic.forgery.AbstractForger;

public class RandomLongForger extends AbstractForger<Long> {
	public Long forge() {
		return new Random().nextLong();
	}
}
