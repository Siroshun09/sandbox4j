package com.github.siroshun09.sandbox4j.okopointshop.serialization;

import com.github.siroshun09.configapi.api.Configuration;
import com.github.siroshun09.configapi.api.MappedConfiguration;
import com.github.siroshun09.configapi.api.serializer.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


public interface Deserializer<I, O> extends Function<I, O> {

    @SuppressWarnings("unchecked")
    Deserializer<Object, Configuration> CONFIGURATION = input -> {
        if (input instanceof Configuration) {
            return (Configuration) input;
        } else if (input instanceof Map) {
            return MappedConfiguration.create((Map<Object, Object>) input);
        } else {
            return MappedConfiguration.create();
        }
    };

    @NotNull O deserialize(@NotNull I input);

    @Override
    default O apply(I i) {
        return deserialize(i);
    }

    default <V> Deserializer<V, O> compose(@NotNull Function<? super V, ? extends I> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> Deserializer<I, V> andThen(@NotNull Function<? super O, ? extends V> after) {
        Objects.requireNonNull(after);
        return (I t) -> after.apply(apply(t));
    }

    default @NotNull Serializer<O, I> toConfigAPI() { // FIXME: WANNA REMOVE
        return new Serializer<>() {
            @Override
            public @NotNull I serialize(@NotNull O input) {
                throw new UnsupportedOperationException();
            }

            @SuppressWarnings("unchecked")
            @Override
            public @NotNull O deserialize(@NotNull Object source) {
                return Deserializer.this.deserialize((I) source);
            }
        };
    }
}
