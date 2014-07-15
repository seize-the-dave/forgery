package uk.co.adaptivelogic.forgery.forger;

import java.util.Random;

import uk.co.adaptivelogic.forgery.AbstractForger;

public class RandomIntegerForger extends AbstractForger<Integer> {
	public Integer forge() {
		return new Random().nextInt();
	}
}
