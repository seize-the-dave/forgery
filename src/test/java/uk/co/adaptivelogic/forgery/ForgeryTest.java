package uk.co.adaptivelogic.forgery;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.adaptivelogic.forgery.domain.Address;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;
import static uk.co.adaptivelogic.forgery.Forgery.forge;

/**
 * Unit test to test and document the basic usage of {@link Forgery}
 */
public class ForgeryTest {

    @Rule
    public ExpectedException expectedException = none();

    @Test
    public void shouldCreateBasicInstanceOfClass() {
        // When
        Address address = forge(Address.class);

        // Then
        assertThat(address, is(notNullValue()));
    }

    @Test
    public void shouldThrowNullPointerWhenPassingNullObjectWithUsefulMessageForUser() {
        // Then
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Mission Impossible attempting to forge null classes :)");

        // When
        forge(null);
    }

}
