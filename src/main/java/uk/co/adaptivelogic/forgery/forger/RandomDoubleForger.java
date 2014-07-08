package uk.co.adaptivelogic.forgery.forger;

import uk.co.adaptivelogic.forgery.Forger;

import java.util.Random;

public class RandomDoubleForger implements Forger<Double> {
	public Double forge() {
		return new Random().nextDouble();
	}
}
