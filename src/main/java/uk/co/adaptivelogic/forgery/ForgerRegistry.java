package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;

import javax.inject.Provider;
import java.lang.reflect.Type;

public interface ForgerRegistry {
    public <T> void register(Provider<T> forger);
    public <T> Optional<Provider<T>> lookup(Type type);
    public <T> Optional<Provider<T>> lookup(Type type, String property);
}
