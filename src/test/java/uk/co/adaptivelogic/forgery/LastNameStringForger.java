package uk.co.adaptivelogic.forgery;

@Property("lastName")
public class LastNameStringForger extends AbstractForger<String> {
	public String forge() {
		return "Smith";
	}
}
