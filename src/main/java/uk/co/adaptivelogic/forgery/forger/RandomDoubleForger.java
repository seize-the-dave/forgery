package uk.co.adaptivelogic.forgery.forger;

import java.util.Random;

import uk.co.adaptivelogic.forgery.AbstractForger;

public class RandomDoubleForger extends AbstractForger<Double> {
	public Double forge() {
		return new Random().nextDouble();
	}
}
