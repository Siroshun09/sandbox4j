package com.github.siroshun09.sandbox4j.okopointshop.serialization;

import com.github.siroshun09.configapi.api.Configuration;
import com.github.siroshun09.sandbox4j.okopointshop.model.registry.ConfigurationDeserializerRegistry;
import com.github.siroshun09.sandbox4j.okopointshop.util.EmptyStringChecker;
import org.jetbrains.annotations.NotNull;

// for Item, Content, Discounter, and Period
public class FindingTypeDeserializer<T> implements Deserializer<Configuration, T> {

    private final ConfigurationDeserializerRegistry<T> registry;

    public FindingTypeDeserializer(@NotNull ConfigurationDeserializerRegistry<T> registry) {
        this.registry = registry;
    }

    @Override
    public @NotNull T deserialize(@NotNull Configuration source) {
        var type = EmptyStringChecker.isDefined(source.getString("type"), "type");

        var deserializer = registry.get(type);

        if (deserializer.isEmpty()) {
            throw new IllegalArgumentException("unknown type: " + type);
        }

        return deserializer.get().deserialize(source);
    }
}
