package uk.co.adaptivelogic.forgery.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager type
 */
public class Manager extends Employee {
	private List<Employee> subordinates = new ArrayList<Employee>();

	public List<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<Employee> subordinates) {
		this.subordinates = subordinates;
	}
}
