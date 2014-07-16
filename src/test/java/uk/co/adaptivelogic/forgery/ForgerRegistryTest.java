package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;
import uk.co.adaptivelogic.forgery.domain.CompositeFixture;
import uk.co.adaptivelogic.forgery.forger.RandomLongForger;

import javax.inject.Provider;

import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class ForgerRegistryTest {
    private ForgerRegistry registry;
    
    @Before
    public void assignRegistry() {
        registry = getForgerRegistry();
    }
    
    public abstract ForgerRegistry getForgerRegistry();


    @Test
    public void shouldFindTypeForger() {
        // Given
        Random random = mock(Random.class);
        when(random.nextLong()).thenReturn(1010L);
        Provider<Long> expected = new RandomLongForger(random);
        registry.register(expected);

        // When
        Optional<Provider<Long>> actual = registry.lookup(Long.class);

        // Then
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().get(), is(1010L));
    }

    @Test
    public void shouldNotFindTypeForgerForInvalidType() {
        // When
        Optional<Provider<Long>> actual = registry.lookup(Long.class);

        // Then
        System.out.println(actual);
        assertThat(actual.isPresent(), is(false));
    }

    @Test
    public void shouldFindPropertyForgerForProperty() {
        // Given
        Provider<String> expected = new FirstNameStringForger();
        registry.register(expected);

        // When
        Optional<Provider<String>> actual = registry.lookup(String.class, "firstName");

        // Then
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().get(), is("John"));
    }

    @Test
    public void shouldNotFindPropertyForgerForInvalidProperty() {
        // Given
        Provider<String> expected = new FirstNameStringForger();
        registry.register(expected);

        // When
        Optional<Provider<String>> actual = registry.lookup(String.class, "lastName");

        // Then
        assertThat(actual.isPresent(), is(false));
    }
    
    @Test
    public void shouldUseCompositeForger() {
        // Given
        registry.register(FirstNameStringForger.class);
        registry.register(LastNameStringForger.class);
        registry.register(NameForger.class);
        
        // When
        Optional<Provider<String>> actual = registry.lookup(String.class, "name");
        
        // Then
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().get(), is("John Smith"));
    }
}
