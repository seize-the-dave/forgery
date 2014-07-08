package uk.co.adaptivelogic.forgery;

@Property("lastName")
public class LastNameStringForger implements Forger<String> {
	public String forge() {
		return "Smith";
	}
}
