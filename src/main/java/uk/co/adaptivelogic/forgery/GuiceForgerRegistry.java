package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.inject.*;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GuiceForgerRegistry extends ForgerRegistrySupport implements ForgerRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiceForgerRegistry.class);
    private Map<Key, Provider<?>> forgerMap = new HashMap<Key, Provider<?>>();
    
    @Override
    public <T> void register(Provider<T> forger) {
        LOGGER.info("Registering " + forger.getClass());
        Type forgerType = getParameterType(forger);
        if (forger.getClass().getAnnotation(Property.class) == null) {
            registerTypeForger(forgerType, forger);
        } else {
            registerPropertyForger(forgerType, forger);
        }
    }
    
    private Injector getInjector() {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                for (Map.Entry<Key, Provider<?>> mapping : forgerMap.entrySet()) {
                    bind(mapping.getKey()).toProvider(Providers.guicify(mapping.getValue()));
                }
            }
        });
    }
    
    private <T> void registerTypeForger(Type forgerType, Provider<T> forger) {
        LOGGER.info("Registering " + forger.getClass() + " as a type Forger for " + forgerType);
        forgerMap.put(Key.get(forgerType), forger);
    }

    private <T> void registerPropertyForger(Type forgerType, Provider<T> forger) {
        LOGGER.info("Registering " + forger.getClass() + " as a property Forger");
        for (String name : forger.getClass().getAnnotation(Property.class).value()) {
            forgerMap.put(Key.get(forgerType, Names.named(name)), forger);
        }
    }

    @Override
    public <T> Optional<Provider<T>> lookup(Type type) {
        LOGGER.info("Looking up Forger for " + type);
        try {
            LOGGER.info("Forger found for " + type);
            return Optional.of((Provider<T>) getInjector().getProvider(Key.get(type)));
        } catch (ConfigurationException e) {
            LOGGER.warn("Forger not found for " + type);
            return Optional.absent();
        }
    }

    @Override
    public <T> Optional<Provider<T>> lookup(Type type, String property) {
        LOGGER.info("Looking up Forger for " + type + " and property '" + property + "'");
        try {
            LOGGER.info("Forger found for " + type);
            return Optional.of((Provider<T>) getInjector().getProvider(Key.get(type, Names.named(property))));
        } catch (ConfigurationException e) {
            LOGGER.warn("Forger not found for " + type + " and property '" + property + "'");
            return Optional.absent();
        }
    }
}
