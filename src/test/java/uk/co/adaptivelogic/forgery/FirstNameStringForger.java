package uk.co.adaptivelogic.forgery;

@Property("firstName")
public class FirstNameStringForger implements Forger<String> {
	public String forge() {
		return "John";
	}
}
