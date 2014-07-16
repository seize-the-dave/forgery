package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;

import javax.inject.Provider;
import java.lang.reflect.Type;

public interface ForgerRegistry {
    public void register(Provider<?> forger);
    public void register(Class<? extends Provider<?>> forgerClass);
    public <T> Optional<? extends Provider<T>> lookup(Type type);
    public <T> Optional<? extends Provider<T>> lookup(Type type, String property);
}
