package uk.co.adaptivelogic.forgery;

import com.google.common.base.Optional;

import java.lang.reflect.Type;

public interface ForgerRegistry {
    public <T> Optional<Forger<T>> lookup(Type type);
    public <T> Optional<Forger<T>> lookup(Type type, String property);
}
