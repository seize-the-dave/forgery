package uk.co.adaptivelogic.forgery;

import java.util.Random;

public class RandomLongForger implements Forger<Long> {
	public Long forge() {
		return new Random().nextLong();
	}
}
