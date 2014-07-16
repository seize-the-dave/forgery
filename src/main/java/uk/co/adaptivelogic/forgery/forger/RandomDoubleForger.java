package uk.co.adaptivelogic.forgery.forger;

import javax.inject.Provider;
import java.util.Random;

public class RandomDoubleForger implements Provider<Double> {
	public Double get() {
		return new Random().nextDouble();
	}
}
