package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Entry point for the forgery of all domain objects.
 *
 * <p>
 *     If you need to auto create a class object then pass it to Forgery and let us provide a properly constructed
 *     class for your use.  All you need to do is <pre>Forgery.forge(ToForge.class)</pre>
 * </p>
 */
public class Forgery {
    
    private Map<Class<?>, ForgerMap<?>> choices;
    
    public Forgery() {
        choices = new HashMap<Class<?>, ForgerMap<?>>();
        Forger<String> firstName = new ConcreteForger<String>(Lists.newArrayList("David", "Lea"));
        Forger<String> lastName = new ConcreteForger<String>(Lists.newArrayList("Grant", "Farmer"));
        ForgerMap<String> forgerMap = new ConcreteForgerMap<String>();
        forgerMap.add("firstName", firstName);
        forgerMap.add("lastName", lastName);
        choices.put(String.class, forgerMap);
    }

    private <T> T forgeFor(@Nonnull Class<T> type) {
        T generatedType;

        try {
            generatedType = checkNotNull(type, MISSION_IMPOSSIBLE).newInstance();
            if (type.equals(String.class)) {
                return generatedType;
            }
            BeanInfo beanInfo = Introspector.getBeanInfo(type, Object.class);
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
                Object property = forge(descriptor.getPropertyType(), descriptor.getName());
                descriptor.getWriteMethod().invoke(generatedType, property);
            }
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }

        return generatedType;
    } 
    
    private static final String MISSION_IMPOSSIBLE = "Mission Impossible attempting to forge null classes :)";

    private <T> T forge(Class<T> type, String name) {
        T generatedType;
        
        try {
            Optional<ForgerMap<T>> generator = getForger(type);
            if (generator.isPresent()) {
                ForgerMap<T> forgerMap = generator.get();
                Optional<Forger<T>> forger = forgerMap.forgerFor(name);
                if (forger.isPresent()) {
                    return forger.get().forge();
                } else {
                    return forge(type);
                }
            } else {
                return forge(type);
            }
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public static <T> T forge(Class<T> type) {
        return new Forgery().forgeFor(type);
    }
    
    private <T> Optional<ForgerMap<T>> getForger(Class<T> type) {
        if (choices.containsKey(type)) {
            return Optional.of((ForgerMap<T>) choices.get(type));
        } else {
            return Optional.absent();
        }
    }
    
    public class ConcreteForger<T> implements Forger<T> {
        private List<T> choices;
        
        public ConcreteForger(List<T> choices) {
            this.choices = choices;
        }
        
        public T forge() {
            return choices.get(0);
        }
    }
    
    public class ConcreteForgerMap<T> implements ForgerMap<T> {
        Map<String, Forger<T>> map;
        
        public ConcreteForgerMap() {
            this.map = new HashMap<String, Forger<T>>();
        }
        
        public void add(String name, Forger<T> forger) {
            map.put(name, forger);
        }
        
        public Optional<Forger<T>> forgerFor(String name) {
            if (map.containsKey(name)) {
                return Optional.of(map.get(name));
            } else {
                return Optional.absent();
            }
        }
    }
    
    private interface Forger<T> {
        public T forge();
    }
    
    private interface ForgerMap<T> {
        public void add(String name, Forger<T> forger);
        public Optional<Forger<T>> forgerFor(String name);
    }
}
