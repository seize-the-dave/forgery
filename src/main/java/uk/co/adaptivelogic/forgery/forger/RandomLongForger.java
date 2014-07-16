package uk.co.adaptivelogic.forgery.forger;

import javax.inject.Provider;
import java.util.Random;

public class RandomLongForger implements Provider<Long> {
    private Random random;
    
    public RandomLongForger(Random random) {
        this.random = random;
    }
    
    public RandomLongForger() {
        this(new Random());
    }
    
	public Long get() {
		return random.nextLong();
	}
    
    public boolean equals(Object o) {
        return o.getClass().equals(RandomLongForger.class);
    }
}
