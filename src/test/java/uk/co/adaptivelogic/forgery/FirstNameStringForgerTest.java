package uk.co.adaptivelogic.forgery;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uk.co.adaptivelogic.forgery.domain.Employee;

public class FirstNameStringForgerTest {

	@Before
	public void setUp() {

	}

	@Test
	public void shouldFillPropertyWithRelevantData() throws ServiceException {
		// When
		Employee employee = new Forgery.Builder()
				.withForger(new FirstNameStringForger())
				.withForger(new LastNameStringForger())
				.build().forge(Employee.class);

		// Then
		assertThat(employee.getFirstName(), equalTo("John"));
		assertThat(employee.getLastName(), equalTo("Smith"));
	}

	@Test
	public void shouldFillPropertyWithRelevantDataUsingDataSource() throws ServiceException {
		// When
		ServiceLocator locator = new InMemoryServiceLocator();
		locator.registerDataSource("firstNames", new ForgerDataSource<String>() {

			@Override
			public Set<String> getValues(Locale locale) {
				Set<String> values = new HashSet<String>();
				values.add("Dave");
				return values;
			}

			@Override
			public String getNextValue(Locale locale) {
				return "Dave";
			}
		});

		Employee employee = new Forgery.Builder()
				.withServiceLocator(locator)
				.withForger(new FirstNameStringForger())
				.withForger(new LastNameStringForger())
				.build().forge(Employee.class);

		// Then
		assertThat(employee.getFirstName(), equalTo("Dave"));
		assertThat(employee.getLastName(), equalTo("Smith"));
	}
}
