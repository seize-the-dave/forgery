package uk.co.adaptivelogic.forgery;

import org.junit.Test;
import uk.co.adaptivelogic.forgery.domain.Address;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit test to test and document the basic usage of {@link Forgery}
 */
public class ForgeryTest {

    @Test
    public void shouldCreateBasicInstanceOfClass() {
        // When
        Address address = Forgery.forge(Address.class);

        // Then
        assertThat(address, is(notNullValue()));
    }
}
