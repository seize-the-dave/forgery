package uk.co.adaptivelogic.forgery;

import org.junit.Test;
import uk.co.adaptivelogic.forgery.domain.Employee;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Unit test to test and document the basic usage of {@link Forgery}
 */
public class ForgeryTest {

    @Test
    public void shouldCreateInstanceOfClassWithProperties() {
        for (int i = 0; i < 10; i++) {
            // When
            Employee employee = new Forgery.Builder().build().forge(Employee.class);

            System.out.println("Employee: " + employee);

            // Then
            assertThat(employee, is(notNullValue()));
            assertThat(employee.getFirstName(), is(notNullValue()));
            assertThat(employee.getLastName(), is(notNullValue()));
            assertThat(employee.isActive(), is(true)); //active is a primitive boolean
        }
    }
}
