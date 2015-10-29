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
    private Map<Key, Class<? extends Provider<?>>> forgerClassMap = new HashMap<Key, Class<? extends Provider<?>>>();
    
    public void register(Provider<?> forger) {
        LOGGER.info("Registering " + forger.getClass());
        Type forgerType = getParameterType(forger);
        if (forger.getClass().getAnnotation(Property.class) == null) {
            registerTypeForger(forgerType, forger);
        } else {
            registerPropertyForger(forgerType, forger);
        }
    }
    
    public void register(Class<? extends Provider<?>> forgerClass) {
        LOGGER.info("Registering " + forgerClass);
        Type forgerType = getParameterType(forgerClass);
        if (forgerClass.getAnnotation(Property.class) != null) {
            LOGGER.info("Registering " + forgerClass + " as a property Forger");
            for (String name : forgerClass.getAnnotation(Property.class).value()) {
                forgerClassMap.put(Key.get(forgerType, Names.named(name)), forgerClass);
            }
        } else {
            LOGGER.info("Registering " + forgerClass + " as a type Forger for " + forgerType);
            forgerClassMap.put(Key.get(forgerType), forgerClass);
        }
    }
    
    private Injector getInjector() {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                for (Map.Entry<Key, Provider<?>> mapping : forgerMap.entrySet()) {
                    LOGGER.info("Binding " + mapping.getValue() + " to " + mapping.getKey());
                    bind(mapping.getKey()).toProvider(Providers.guicify(mapping.getValue()));
                }
                for (Map.Entry<Key, Class<? extends Provider<?>>> mapping : forgerClassMap.entrySet()) {
                    LOGGER.info("Binding " + mapping.getValue() + " to " + mapping.getKey());
                    bind(mapping.getKey()).toProvider(mapping.getValue());
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

    public Optional<? extends Provider<?>> lookup(Type type) {
        LOGGER.info("Looking up Forger for " + type);
        Binding<?> binding = getInjector().getExistingBinding(Key.get(type));
        if (binding == null) {
            LOGGER.warn("Forger not found for " + type);
            return Optional.absent(); 
        } else {
            LOGGER.info("Forger found for " + type);
            return Optional.of(binding.getProvider());
        }
    }

    public Optional<? extends Provider<?>> lookup(Type type, String property) {
        LOGGER.info("Looking up Forger for " + type + " and property '" + property + "'");
        Binding<?> binding = getInjector().getExistingBinding(Key.get(type, Names.named(property)));
        if (binding == null) {
            LOGGER.warn("Forger not found for " + type + " and property '" + property + "'");
            return Optional.absent();
        } else {
            LOGGER.info("Forger found for " + type);
            return Optional.of(binding.getProvider());
        }
    }
}
