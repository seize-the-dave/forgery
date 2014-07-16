package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryForgerRegistry implements ForgerRegistry {
    private Map<Type, Forger<?>> typeMap = new HashMap<Type, Forger<?>>();
    private Map<Type, ForgerCollection<?>> parameterMap = new HashMap<Type, ForgerCollection<?>>();
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryForgerRegistry.class);
    private static final int FIRST_PARAMETER = 0;
    
    public InMemoryForgerRegistry(Forger<?>... forgerList) {
        for (Forger<?> forger : forgerList) {
            register(forger);
        }
    }
    
    private <T> void register(Forger<T> forger) {
        LOGGER.info("Registering " + forger.getClass());
        Type forgerType = getParameterType(forger);
        if (forger.getClass().getAnnotation(Property.class) == null) {
            registerTypeForger(forgerType, forger);
        } else {
            registerParameterForger(forgerType, forger);
        }
    }

    private <T> void registerParameterForger(Type forgerType, Forger<T> forger) {
        LOGGER.info("Registering " + forger.getClass() + " as a property Forger");
        ForgerCollection<T> forgerCollection;
        if (!parameterMap.containsKey(forgerType)) {
            forgerCollection = new ForgerCollection<T>();
            parameterMap.put(forgerType, forgerCollection);
        } else {
            forgerCollection = (ForgerCollection<T>) parameterMap.get(forgerType);
        }
        forgerCollection.add(forger);
    }

    private <T> void registerTypeForger(Type forgerType, Forger<T> forger) {
        LOGGER.info("Registering " + forger.getClass() + " as a type Forger for " + forgerType);
        typeMap.put(forgerType, forger);
    }

    @Override
    public <T> Optional<Forger<T>> lookup(Type type) {
        LOGGER.info("Looking up Forger for " + type);
        if (typeMap.containsKey(type)) {
            LOGGER.info("Forger found for " + type);
            return Optional.of((Forger<T>) typeMap.get(type));
        } else {
            LOGGER.warn("Forger not found for " + type);
            return Optional.absent();
        }
    }

    @Override
    public <T> Optional<Forger<T>> lookup(Type type, String property) {
        LOGGER.info("Looking up Forger for " + type + " and property '" + property + "'");
        if (parameterMap.containsKey(type)) {
            ForgerCollection<T> forgerCollection = (ForgerCollection<T>) parameterMap.get(type);

            LOGGER.info("One or more property Forgers found for " + type);
            return forgerCollection.lookup(property);
        } else {
            LOGGER.warn("Forger not found for " + type + " and property '" + property + "'");
            return Optional.absent();
        }
    }

    private Type getParameterType(Forger<?> forger) {
        Type forgerType = TypeToken.of(forger.getClass()).getSupertype(Forger.class).getType();
        ParameterizedType parameterizedType = ParameterizedType.class.cast(forgerType);

        return parameterizedType.getActualTypeArguments()[FIRST_PARAMETER];
    }
}
