package com.github.siroshun09.sandbox4j.okopointshop.builtin;

import com.github.siroshun09.configapi.api.Configuration;
import com.github.siroshun09.sandbox4j.okopointshop.model.discount.Discounter;
import com.github.siroshun09.sandbox4j.okopointshop.model.registry.ConfigurationDeserializerRegistry;
import org.jetbrains.annotations.NotNull;

public class BuiltinDiscounterDeserializers {

    public static void addToRegistry(@NotNull ConfigurationDeserializerRegistry<Discounter> registry) {
        registry.register("percent_off", source -> Discounter.percentOff(readValue(source)));
        registry.register("price_cut", source -> Discounter.priceCut(readValue(source)));
        registry.register("free", source -> Discounter.FREE);
        registry.register("free_trial", source -> Discounter.freeTrial(u -> false)); // TODO: need purchase history
    }

    private static double readValue(@NotNull Configuration source) {
        return source.getDouble("value", source.getInteger("value"));
    }
}
