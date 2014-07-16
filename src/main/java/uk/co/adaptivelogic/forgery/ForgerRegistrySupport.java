package uk.co.adaptivelogic.forgery;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ForgerRegistrySupport {
    private static final int FIRST_PARAMETER = 0;
    
    protected Type getParameterType(Forger<?> forger) {
        Type forgerType = TypeToken.of(forger.getClass()).getSupertype(Forger.class).getType();
        ParameterizedType parameterizedType = ParameterizedType.class.cast(forgerType);

        return parameterizedType.getActualTypeArguments()[FIRST_PARAMETER];
    }
}
