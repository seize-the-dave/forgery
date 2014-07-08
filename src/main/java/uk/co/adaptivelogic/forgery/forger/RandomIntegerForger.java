package uk.co.adaptivelogic.forgery.forger;

import uk.co.adaptivelogic.forgery.Forger;

import java.util.Random;

public class RandomIntegerForger implements Forger<Integer> {
	public Integer forge() {
		return new Random().nextInt();
	}
}
