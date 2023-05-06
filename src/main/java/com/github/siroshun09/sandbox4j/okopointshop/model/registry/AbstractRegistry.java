package com.github.siroshun09.sandbox4j.okopointshop.model.registry;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRegistry<T> implements Registry<T> {

    private final Map<String, T> backing = new ConcurrentHashMap<>();

    @Override
    public void register(@NotNull String id, @NotNull T type) {
        backing.put(id, type);
    }

    @Override
    public void unregister(@NotNull String id) {
        backing.remove(id);
    }

    @Override
    public @NotNull Optional<T> get(@NotNull String id) {
        return Optional.ofNullable(backing.get(id));
    }
}
