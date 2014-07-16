package uk.co.adaptivelogic.forgery.forger;

import javax.inject.Provider;
import java.util.Random;

public class RandomIntegerForger implements Provider<Integer> {
	public Integer get() {
		return new Random().nextInt();
	}
}
