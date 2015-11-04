package uk.co.adaptivelogic.forgery;

import com.google.inject.Provider;
import org.junit.Test;
import uk.co.adaptivelogic.forgery.domain.Employee;
import uk.co.adaptivelogic.forgery.domain.Manager;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.co.adaptivelogic.forgery.ForgeryBuilder.forgeryBuilder;

public class ForgeryBuilderTest {

    @Test
    public void shouldProvideForgeryForSupportedProvidersWithoutTheNeedForAddingProviders() {
        // When
        DefaultPojo defaultPojo = forgeryBuilder().build().forge(DefaultPojo.class);

        // Then
        assertThat(defaultPojo.getPlainOldDate(), is(notNullValue()));
        assertThat(defaultPojo.getPlainOldDouble(), is(notNullValue()));
        assertThat(defaultPojo.getPlainOldInteger(), is(notNullValue()));
        assertThat(defaultPojo.getPlainOldLong(), is(notNullValue()));

        assertThat(defaultPojo.getCreditCardNumber(), is(notNullValue()));
        assertThat(defaultPojo.getDateOfBirth(), is(notNullValue()));
        assertThat(defaultPojo.getNationalInsurance(), is(notNullValue()));
        assertThat(defaultPojo.getSocialSecurityNumber(), is(notNullValue()));
    }

    @Test
    public void shouldWorkMultipleTimesAndProvideDifferentForgeries() {
        // When
        Employee employee = forgeryBuilder().build().forge(Employee.class);
        Employee employee2 = forgeryBuilder().build().forge(Employee.class);

        // Then
        assertThat(employee, is(not(employee2)));
    }

    @Test
    public void shouldAllowUsersToSupplyTheirOwnForgers() {
        // Given
        Forgery forgery = forgeryBuilder()
                .withForger(new FirstNameStringForger())
                .withForger(new LastNameStringForger())
                .build();

        // When
        Employee employee = forgery.forge(Employee.class);

        // Then
        assertThat(employee.getFirstName(), is("John"));
        assertThat(employee.getLastName(), is("Smith"));
    }

    @Test
    public void shouldAllowForgingOfParameterizedTypes() {
        // Given
        Forgery forgery = forgeryBuilder()
                .withForger(new FirstNameStringForger())
                .withForger(new LastNameStringForger())
                .withForger(new Provider<List<Employee>>() {
                    public List<Employee> get() {
                        return new ArrayList<Employee>();
                    }
                }).build();

        // When
        Manager manager = forgery.forge(Manager.class);

        // Then
        for (Employee employee : manager.getSubordinates()) {
            assertThat(employee.getFirstName(), is(notNullValue()));
            assertThat(employee.getLastName(), is(notNullValue()));
        }
    }

}
