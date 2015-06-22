package uk.co.adaptivelogic.forgery;

import javax.inject.Provider;

@Property("active")
public class ActiveForger implements Provider<Boolean> {

	public Boolean get() {
		return Boolean.TRUE;
	}
}
