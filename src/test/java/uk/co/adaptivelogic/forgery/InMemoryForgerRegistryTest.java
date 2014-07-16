package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;
import uk.co.adaptivelogic.forgery.forger.RandomLongForger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InMemoryForgerRegistryTest extends ForgerRegistryTest {
    public ForgerRegistry getForgerRegistry() {
        return new InMemoryForgerRegistry();
    }
}
