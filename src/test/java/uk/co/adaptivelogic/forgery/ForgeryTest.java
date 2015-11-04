package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.inject.Provider;
import java.lang.reflect.Type;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.rules.ExpectedException.none;

/**
 * Unit test to test and document the basic usage of {@link Forgery}
 */
public class ForgeryTest {

    @Rule
    public ExpectedException expectedException = none();

    @Test
    public void shouldThrowNullPointerWhenPassingNullObjectWithUsefulMessageForUser() {
        // Then
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Mission Impossible attempting to get null classes :)");

        // When
        Forgery forgery = new Forgery(new FakeForgerRegistry());
        forgery.forge(null);
    }

    @Test
    public void shouldFailForInaccessibleType() {
        // Then
        expectedException.expectCause(isA(IllegalAccessException.class));

        // When
        Forgery forgery = new Forgery(new FakeForgerRegistry());
        forgery.forge(System.class);
    }

    @Test
    public void shouldFailForUninstantiableType() {
        // Then
        expectedException.expectCause(isA(InstantiationException.class));

        // When
        Forgery forgery = new Forgery(new FakeForgerRegistry());
        forgery.forge(Forgery.class);
    }

    private static class FakeForgerRegistry implements ForgerRegistry {
        public void register(Provider<?> forger) {
            // Do nothing
        }
        
        public void register(Class<? extends Provider<?>> forgerClass) {
            // Do nothing
        }
        
        public <T> Optional<Provider<T>> lookup(Type type) {
            return Optional.absent();
        }

        public <T> Optional<Provider<T>> lookup(Type type, String property) {
            return Optional.absent();
        }
    }
}
