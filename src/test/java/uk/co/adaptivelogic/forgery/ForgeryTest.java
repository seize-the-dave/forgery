package uk.co.adaptivelogic.forgery;

import org.junit.Test;
import uk.co.adaptivelogic.forgery.domain.Person;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit test to test and document the basic usage of {@link Forgery}
 */
public class ForgeryTest {

    @Test
    public void shouldCreateBasicInstanceOfClass() {
        // When
        Person person = Forgery.forge(Person.class);

        // Then
        assertThat(person, is(notNullValue()));
        assertThat(person.getFirstName(), is(notNullValue()));
        assertThat(person.getLastName(), is(notNullValue()));
        
        System.out.println(person);
    }
}
