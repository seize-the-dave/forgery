package uk.co.adaptivelogic.forgery.domain;

import com.google.common.base.Objects;

/**
 * Person type
 */
public class Person {
	private String firstName;
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String toString() {
		return Objects.toStringHelper(this).add("firstName", firstName).add("lastName", lastName).toString();
	}
}
