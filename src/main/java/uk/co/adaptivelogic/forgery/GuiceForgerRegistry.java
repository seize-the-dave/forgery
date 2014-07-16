package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GuiceForgerRegistry extends ForgerRegistrySupport implements ForgerRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiceForgerRegistry.class);
    private Map<Key, Forger<?>> forgerMap = new HashMap<Key, Forger<?>>();
    
    @Override
    public <T> void register(Forger<T> forger) {
        LOGGER.info("Registering " + forger.getClass());
        Type forgerType = getParameterType(forger);
        if (forger.getClass().getAnnotation(Property.class) == null) {
            registerTypeForger(forgerType, forger);
        } else {
            registerPropertyForger(forgerType, forger);
        }
    }

    private <T> void registerTypeForger(Type forgerType, Forger<T> forger) {
        LOGGER.info("Registering " + forger.getClass() + " as a type Forger for " + forgerType);
        forgerMap.put(Key.get(forgerType), forger);
    }

    private <T> void registerPropertyForger(Type forgerType, Forger<T> forger) {
        LOGGER.info("Registering " + forger.getClass() + " as a property Forger");
        for (String name : forger.getClass().getAnnotation(Property.class).value()) {
            forgerMap.put(Key.get(forgerType, Names.named(name)), forger);
        }
    }

    @Override
    public <T> Optional<Forger<T>> lookup(Type type) {
        LOGGER.info("Looking up Forger for " + type);
        if (forgerMap.containsKey(Key.get(type))) {
            LOGGER.info("Forger found for " + type);
            return Optional.of((Forger<T>) forgerMap.get(Key.get(type)));
        } else {
            LOGGER.warn("Forger not found for " + type);
            return Optional.absent();
        }
    }

    @Override
    public <T> Optional<Forger<T>> lookup(Type type, String property) {
        LOGGER.info("Looking up Forger for " + type + " and property '" + property + "'");
        if (forgerMap.containsKey(Key.get(type, Names.named(property)))) {
            LOGGER.info("Forger found for " + type);
            return Optional.of((Forger<T>) forgerMap.get(Key.get(type, Names.named(property))));
        } else {
            LOGGER.warn("Forger not found for " + type + " and property '" + property + "'");
            return Optional.absent();
        }
    }
}
