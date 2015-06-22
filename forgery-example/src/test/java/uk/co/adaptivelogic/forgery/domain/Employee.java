package uk.co.adaptivelogic.forgery.domain;

import com.google.common.base.Objects;

import java.util.Date;

/**
 * Employee type
 */
public class Employee {
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String ssn;
	private boolean active;

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String toString() {
		return Objects.toStringHelper(this)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("dateOfBirth", dateOfBirth)
				.add("ssn", ssn)
				.add("active", active)
				.toString();
	}
}
