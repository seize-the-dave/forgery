package uk.co.adaptivelogic.forgery;

import com.google.common.reflect.TypeToInstanceMap;
import com.google.common.reflect.TypeToken;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.adaptivelogic.forgery.domain.Employee;
import uk.co.adaptivelogic.forgery.domain.Manager;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;

/**
 * Unit test to test and document the basic usage of {@link Forgery}
 */
public class ForgeryTest {

	@Rule
	public ExpectedException expectedException = none();

	@Test
	public void shouldCreateInstanceOfClass() {
		// When
		String string = new Forgery().forge(String.class);

		// Then
		assertThat(string, is(notNullValue()));
	}

	@Test
	public void shouldCreateInstanceOfClassWithProperties() {
		// When
		Employee person = new Forgery().forge(Employee.class);

		// Then
		assertThat(person, is(notNullValue()));
		assertThat(person.getFirstName(), is(notNullValue()));
		assertThat(person.getLastName(), is(notNullValue()));
	}

	@Test
	public void shouldUseForgerForForgingClass() {
		Forger<String> forger = new Forger<String>() {
			public String forge() {
				return "Example";
			}
		};
		// When
		String string = new Forgery(forger).forge(String.class);

		// Then
		assertThat(string, is("Example"));
	}

	@Test
	public void shouldThrowNullPointerWhenPassingNullObjectWithUsefulMessageForUser() {
		// Then
		expectedException.expect(NullPointerException.class);
		expectedException.expectMessage("Mission Impossible attempting to forge null classes :)");

		// When
		new Forgery().forge(null);
	}

	@Test
	public void shouldFailWhenCreatingInstanceWithoutDefaultConstructor() {
		// Then
		expectedException.expectCause(isA(InstantiationException.class));

		// When
		new Forgery().forge(Integer.class);
	}

	@Test
	public void shouldCreateInstanceOfClassUsingLoadedForger() {
		// When
		Long actual = new Forgery().forge(Long.class);

		// Then
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void shouldFillPropertyWithRelevantData() {
		// When
		Employee employee = new Forgery(new FirstNameStringForger(), new LastNameStringForger()).forge(Employee.class);

		// Then
		assertThat(employee.getFirstName(), is("John"));
		assertThat(employee.getLastName(), is("Smith"));
	}

	@Test
	public void shouldCreateClassWithParameterizedProperty() {
		// When
		Manager manager = new Forgery(new FirstNameStringForger(), new LastNameStringForger(), new Forger<List<Employee>>() {
			@Override
			public List<Employee> forge() {
				return new ArrayList<Employee>();
			}
		}).forge(Manager.class);

		// Then
		assertThat(manager.getFirstName(), is("John"));
		assertThat(manager.getLastName(), is("Smith"));
	}

	@Test
	public void shouldCreateParameterizedType() {
		// When
		List<Employee> employees = new Forgery(new Forger<List<Employee>>() {
			@Override
			public List<Employee> forge() {
				return new ArrayList<Employee>();
			}
		}).forge(new TypeToken<List<Employee>>() {}.getType());

		// Then
		assertThat(employees, is(notNullValue()));
	}

}
