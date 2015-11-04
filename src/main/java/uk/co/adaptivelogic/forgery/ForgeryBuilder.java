package uk.co.adaptivelogic.forgery;

import javax.inject.Provider;
import java.util.ServiceLoader;

/**
 * Builds the {@link Forgery} with the ability to add additional {@link Provider}'s to the current list of
 * supported providers already registered within Forgery.
 */
public class ForgeryBuilder {

    private ForgerRegistry registry = new GuiceForgerRegistry();

    private ForgeryBuilder() {
        for (Provider<?> forger : ServiceLoader.load(Provider.class)) {
            registry.register(forger);
        }
    }

    public static ForgeryBuilder forgeryBuilder() {
        return new ForgeryBuilder();
    }

    public ForgeryBuilder withForger(Provider<?> forger) {
        registry.register(forger);
        return this;
    }

    public ForgeryBuilder withForger(Class<? extends Provider<?>> forgerClass) {
        registry.register(forgerClass);
        return this;
    }

    public Forgery build() {
        return new Forgery(registry);
    }
}
