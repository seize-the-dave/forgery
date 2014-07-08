package uk.co.adaptivelogic.forgery;

import java.util.Random;

public class RandomDoubleForger implements Forger<Double> {
	public Double forge() {
		return new Random().nextDouble();
	}
}
