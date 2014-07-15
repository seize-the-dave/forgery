package uk.co.adaptivelogic.forgery;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;

@Property("firstName")
public abstract class AbstractForger<T> implements Forger<T> {
	private ServiceLocator locator;

	public abstract T forge();	

	public ServiceLocator getServiceLocator() {
		return locator;
	}

	@Override
	public void service(@Nonnull ServiceLocator locator) throws ServiceException {
		Validate.notNull(locator, "locator");
		this.locator = locator;
	}
}
