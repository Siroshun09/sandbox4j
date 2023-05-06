package com.github.siroshun09.sandbox4j.okopointshop.model.registry;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Registry<T> {

    void register(@NotNull String id, @NotNull T type);

    void unregister(@NotNull String id);

    @NotNull Optional<T> get(@NotNull String id);

}
