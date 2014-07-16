package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Provider;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Entry point for the forgery of all domain objects.
 * <p/>
 * <p>
 * If you need to auto create a class object then pass it to Forgery and let us provide a properly constructed
 * class for your use.  All you need to do is <pre>Forgery.get(ToForge.class)</pre>
 * </p>
 */
public class Forgery {
    private static final String MISSION_IMPOSSIBLE = "Mission Impossible attempting to get null classes :)";
    private static final Logger LOGGER = LoggerFactory.getLogger(Forgery.class);
    private ForgerRegistry registry;

    public Forgery(ForgerRegistry registry) {
        this.registry = registry;
    }

    public <T> T forge(@Nonnull Type type) {
        try {
            LOGGER.info("Forging " + type);
            Optional<Provider<T>> forger = registry.lookup(checkNotNull(type, MISSION_IMPOSSIBLE));
            if (forger.isPresent()) {
                return forger.get().get();
            } else {
                LOGGER.info("Creating a new instance of " + type + " using no-arg constructor");
                T forgedType = ((Class<T>) type).newInstance();
                LOGGER.info("Forging properties of " + type);
                forgeProperties(type, forgedType);
                LOGGER.info("Forging complete for " + type);

                return forgedType;
            }
        } catch (Exception e) {
            LOGGER.error("Forging failed for " + type, e);
            throw Throwables.propagate(e);
        }
    }

    private <T> T forge(@Nonnull Type type, String property) {
        LOGGER.info("Forging " + type + " for property '" + property + "'");
        Optional<Provider<T>> forger = registry.lookup(type, property);
        if (forger.isPresent()) {
            LOGGER.info("Forging " + type + " for property '" + property + "'");
            return forger.get().get();
        } else {
            LOGGER.warn("Falling back to Forger for " + type);
            return forge(type);
        }
    }

    private <T> void forgeProperties(Type type, T forgedObject) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(forgedObject.getClass());
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : properties) {
            forgeProperty(forgedObject, property);
        }
    }

    private <T> void forgeProperty(T forgedObject, PropertyDescriptor property) throws IllegalAccessException, InvocationTargetException {
        Method write = property.getWriteMethod();
        Method read = property.getReadMethod();
        if (write != null) {
            T value = forge(TypeToken.of(read.getGenericReturnType()).getType(), property.getName());
            LOGGER.info("Set '" + property.getName() + "' to '" + value + "'");
            write.invoke(forgedObject, value);
        }
    }

    public static class Builder {
        private ForgerRegistry registry = new InMemoryForgerRegistry();

        public Builder() {
            for (Provider<?> forger : ServiceLoader.load(Provider.class)) {
                registry.register(forger);
            }
        }

        public Builder withForger(Provider<?> forger) {
            registry.register(forger);
            return this;
        }

        public Forgery build() {
            return new Forgery(registry);
        }
    }
}
