package uk.co.adaptivelogic.forgery;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.junit.Rule;
import org.junit.Test;
import uk.co.adaptivelogic.forgery.domain.Person;
import org.junit.rules.ExpectedException;

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
        Person person = new Forgery().forge(Person.class);
        
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

}
