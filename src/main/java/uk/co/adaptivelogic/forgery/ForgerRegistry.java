package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;

import javax.inject.Provider;
import java.lang.reflect.Type;

public interface ForgerRegistry {
    void register(Provider<?> forger);
    void register(Class<? extends Provider<?>> forgerClass);
    <T> Optional<? extends Provider<T>> lookup(Type type);
    <T> Optional<? extends Provider<T>> lookup(Type type, String property);
}
